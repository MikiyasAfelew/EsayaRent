package com.renteasy.app;

@dagger.hilt.android.HiltAndroidApp()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016\u00a8\u0006\u0006"}, d2 = {"Lcom/renteasy/app/RentEasyApp;", "Landroid/app/Application;", "Lcoil/ImageLoaderFactory;", "()V", "newImageLoader", "Lcoil/ImageLoader;", "app_debug"})
public final class RentEasyApp extends android.app.Application implements coil.ImageLoaderFactory {
    
    public RentEasyApp() {
        super();
    }
    
    /**
     * Global Coil ImageLoader used by every AsyncImage in the app.
     *
     * Key settings:
     * - respectCacheHeaders(false): Firebase Storage and some CDNs send "Cache-Control: no-cache"
     *   which would force Coil to re-download the image on every composition. We override this so
     *   images are cached locally regardless of server headers.
     * - 50 MB disk cache: property listing photos load instantly on re-visit.
     * - 25% memory cache: avoids grey flicker while scrolling the Discovery Feed.
     * - crossfade(400ms): smooth fade-in instead of a jarring snap.
     */
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public coil.ImageLoader newImageLoader() {
        return null;
    }
}