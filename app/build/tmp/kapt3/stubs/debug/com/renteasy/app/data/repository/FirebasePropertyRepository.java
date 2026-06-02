package com.renteasy.app.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J$\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ$\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0012H\u0002J\u0018\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0014\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001e0\u001dH\u0016J\u001c\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001e0\u001d2\u0006\u0010 \u001a\u00020\u0012H\u0016J\u001c\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001e0\u001d2\u0006\u0010\"\u001a\u00020#H\u0016J\u0018\u0010$\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u001d2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J$\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b&\u0010\u000fJ,\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020#H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b(\u0010)J\u001e\u0010*\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00122\u0006\u0010+\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u0010,R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006-"}, d2 = {"Lcom/renteasy/app/data/repository/FirebasePropertyRepository;", "Lcom/renteasy/app/domain/repository/PropertyRepository;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "context", "Landroid/content/Context;", "(Lcom/google/firebase/firestore/FirebaseFirestore;Landroid/content/Context;)V", "storage", "Lcom/google/firebase/storage/FirebaseStorage;", "addProperty", "Lkotlin/Result;", "", "property", "Lcom/renteasy/app/domain/model/Property;", "addProperty-gIAlu-s", "(Lcom/renteasy/app/domain/model/Property;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteProperty", "id", "", "deleteProperty-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBase64ImageFallback", "uriStr", "getFallbackStockImage", "category", "Lcom/renteasy/app/domain/model/PropertyCategory;", "index", "", "getProperties", "Lkotlinx/coroutines/flow/Flow;", "", "getPropertiesByOwner", "ownerId", "getPropertiesByStatus", "status", "Lcom/renteasy/app/domain/model/PropertyStatus;", "getPropertyById", "updateProperty", "updateProperty-gIAlu-s", "updatePropertyStatus", "updatePropertyStatus-0E7RQCE", "(Ljava/lang/String;Lcom/renteasy/app/domain/model/PropertyStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadImageToStorage", "storagePath", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class FirebasePropertyRepository implements com.renteasy.app.domain.repository.PropertyRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.storage.FirebaseStorage storage = null;
    
    @javax.inject.Inject()
    public FirebasePropertyRepository(@org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.FirebaseFirestore firestore, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.renteasy.app.domain.model.Property>> getProperties() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.renteasy.app.domain.model.Property>> getPropertiesByStatus(@org.jetbrains.annotations.NotNull()
    com.renteasy.app.domain.model.PropertyStatus status) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.renteasy.app.domain.model.Property> getPropertyById(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
        return null;
    }
    
    /**
     * Upload a content:// URI to Firebase Storage and return the https download URL.
     */
    private final java.lang.Object uploadImageToStorage(java.lang.String uriStr, java.lang.String storagePath, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final java.lang.String getFallbackStockImage(com.renteasy.app.domain.model.PropertyCategory category, int index) {
        return null;
    }
    
    private final java.lang.String getBase64ImageFallback(java.lang.String uriStr) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.renteasy.app.domain.model.Property>> getPropertiesByOwner(@org.jetbrains.annotations.NotNull()
    java.lang.String ownerId) {
        return null;
    }
}