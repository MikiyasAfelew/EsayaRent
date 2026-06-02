package com.renteasy.app.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eJ(\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ(\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\f\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\r\u00a8\u0006\u000f"}, d2 = {"Lcom/renteasy/app/data/api/ChapaApiService;", "", "initializeTransaction", "Lretrofit2/Response;", "Lcom/renteasy/app/data/api/ChapaInitializeResponse;", "authorization", "", "request", "Lcom/renteasy/app/data/api/ChapaInitializeRequest;", "(Ljava/lang/String;Lcom/renteasy/app/data/api/ChapaInitializeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "verifyTransaction", "Lcom/renteasy/app/data/api/ChapaVerifyResponse;", "txRef", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public abstract interface ChapaApiService {
    @org.jetbrains.annotations.NotNull()
    public static final com.renteasy.app.data.api.ChapaApiService.Companion Companion = null;
    
    @retrofit2.http.POST(value = "v1/transaction/initialize")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object initializeTransaction(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String authorization, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.renteasy.app.data.api.ChapaInitializeRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.renteasy.app.data.api.ChapaInitializeResponse>> $completion);
    
    @retrofit2.http.GET(value = "v1/transaction/verify/{tx_ref}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object verifyTransaction(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String authorization, @retrofit2.http.Path(value = "tx_ref")
    @org.jetbrains.annotations.NotNull()
    java.lang.String txRef, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.renteasy.app.data.api.ChapaVerifyResponse>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/renteasy/app/data/api/ChapaApiService$Companion;", "", "()V", "BASE_URL", "", "create", "Lcom/renteasy/app/data/api/ChapaApiService;", "app_debug"})
    public static final class Companion {
        @org.jetbrains.annotations.NotNull()
        private static final java.lang.String BASE_URL = "https://api.chapa.co/";
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.renteasy.app.data.api.ChapaApiService create() {
            return null;
        }
    }
}