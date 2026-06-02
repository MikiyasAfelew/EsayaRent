package com.renteasy.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

/**
 * A robust, custom Image loader that supports standard HTTP/HTTPS URLs,
 * local cached URIs, AND directly handles data:image/base64 inline strings
 * by decoding them directly in Compose without relying on library fetchers.
 */
@Composable
fun RentEasyImage(
    model: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    if (model == null) {
        // Fallback to stock image
        AsyncImage(
            model = "https://images.unsplash.com/photo-1560518883-ce09059eeffa?auto=format&fit=crop&q=80&w=800",
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
        return
    }

    if (model.startsWith("data:image/") || model.startsWith("data:")) {
        val bitmap = remember(model) {
            try {
                val commaIndex = model.indexOf(',')
                val base64Payload = if (commaIndex >= 0) model.substring(commaIndex + 1) else model
                val bytes = android.util.Base64.decode(base64Payload, android.util.Base64.DEFAULT)
                val decoded = android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                decoded?.asImageBitmap()
            } catch (e: Exception) {
                null
            }
        }

        if (bitmap != null) {
            Image(
                bitmap = bitmap,
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = modifier
            )
        } else {
            // Decoded failed, show placeholder
            Box(
                modifier = modifier.background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Image,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    } else {
        // Standard remote or local image
        AsyncImage(
            model = model,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}
