package com.renteasy.app.data.image;

/**
 * A Coil [Fetcher] that handles `data:image/<type>;base64,<data>` URIs.
 *
 * Coil 2.x natively supports http/https, file, content and android.resource URIs,
 * but NOT the `data:` URI scheme.  When Firebase Storage upload fails for a
 * property listing photo we fall back to storing the image as a Base64-encoded
 * data URI in Firestore.  Without this fetcher those images render as blank/grey
 * boxes in every screen that calls AsyncImage with such a URL.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\nB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/renteasy/app/data/image/Base64Fetcher;", "Lcoil/fetch/Fetcher;", "data", "", "options", "Lcoil/request/Options;", "(Ljava/lang/String;Lcoil/request/Options;)V", "fetch", "Lcoil/fetch/FetchResult;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Factory", "app_debug"})
public final class Base64Fetcher implements coil.fetch.Fetcher {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String data = null;
    @org.jetbrains.annotations.NotNull()
    private final coil.request.Options options = null;
    
    public Base64Fetcher(@org.jetbrains.annotations.NotNull()
    java.lang.String data, @org.jetbrains.annotations.NotNull()
    coil.request.Options options) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object fetch(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super coil.fetch.FetchResult> $completion) {
        return null;
    }
    
    /**
     * Factory that tells Coil which data types this fetcher handles.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\"\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016\u00a8\u0006\u000b"}, d2 = {"Lcom/renteasy/app/data/image/Base64Fetcher$Factory;", "Lcoil/fetch/Fetcher$Factory;", "", "()V", "create", "Lcoil/fetch/Fetcher;", "data", "options", "Lcoil/request/Options;", "imageLoader", "Lcoil/ImageLoader;", "app_debug"})
    public static final class Factory implements coil.fetch.Fetcher.Factory<java.lang.String> {
        
        public Factory() {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public coil.fetch.Fetcher create(@org.jetbrains.annotations.NotNull()
        java.lang.String data, @org.jetbrains.annotations.NotNull()
        coil.request.Options options, @org.jetbrains.annotations.NotNull()
        coil.ImageLoader imageLoader) {
            return null;
        }
    }
}