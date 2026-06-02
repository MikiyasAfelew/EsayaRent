package com.renteasy.app.data.image

import android.graphics.BitmapFactory
import android.util.Base64
import coil.ImageLoader
import coil.decode.DataSource
import coil.decode.ImageSource
import coil.fetch.DrawableResult
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.request.Options
import okio.Buffer

/**
 * A Coil [Fetcher] that handles `data:image/<type>;base64,<data>` URIs.
 *
 * Coil 2.x natively supports http/https, file, content and android.resource URIs,
 * but NOT the `data:` URI scheme.  When Firebase Storage upload fails for a
 * property listing photo we fall back to storing the image as a Base64-encoded
 * data URI in Firestore.  Without this fetcher those images render as blank/grey
 * boxes in every screen that calls AsyncImage with such a URL.
 */
class Base64Fetcher(
    private val data: String,
    private val options: Options
) : Fetcher {

    override suspend fun fetch(): FetchResult {
        // Strip the header: "data:image/jpeg;base64,"  →  the raw Base64 payload
        val commaIndex = data.indexOf(',')
        val base64Payload = if (commaIndex >= 0) data.substring(commaIndex + 1) else data

        val bytes = Base64.decode(base64Payload, Base64.DEFAULT)

        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            ?: error("Failed to decode Base64 image payload")

        val drawable = android.graphics.drawable.BitmapDrawable(
            options.context.resources,
            bitmap
        )

        return DrawableResult(
            drawable = drawable,
            isSampled = false,
            dataSource = DataSource.MEMORY
        )
    }

    /** Factory that tells Coil which data types this fetcher handles. */
    class Factory : Fetcher.Factory<String> {
        override fun create(data: String, options: Options, imageLoader: ImageLoader): Fetcher? {
            if (!data.startsWith("data:image/")) return null
            return Base64Fetcher(data, options)
        }
    }
}
