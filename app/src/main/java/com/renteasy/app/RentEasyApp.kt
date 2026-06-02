package com.renteasy.app

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RentEasyApp : Application(), ImageLoaderFactory {

    /**
     * Global Coil ImageLoader used by every AsyncImage in the app.
     *
     * Key settings:
     *  - respectCacheHeaders(false): Firebase Storage and some CDNs send "Cache-Control: no-cache"
     *    which would force Coil to re-download the image on every composition. We override this so
     *    images are cached locally regardless of server headers.
     *  - 50 MB disk cache: property listing photos load instantly on re-visit.
     *  - 25% memory cache: avoids grey flicker while scrolling the Discovery Feed.
     *  - crossfade(400ms): smooth fade-in instead of a jarring snap.
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .crossfade(400)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25) // 25% of available app memory
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(50L * 1024 * 1024) // 50 MB on-device cache
                    .build()
            }
            .components {
                add(com.renteasy.app.data.image.Base64Fetcher.Factory())
            }
            .respectCacheHeaders(false) // ignore server no-cache headers
            .build()
    }
}
