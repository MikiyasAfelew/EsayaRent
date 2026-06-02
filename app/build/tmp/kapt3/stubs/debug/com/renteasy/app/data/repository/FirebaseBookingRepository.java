package com.renteasy.app.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001c\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\r2\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J\u001c\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\r2\u0006\u0010\u0014\u001a\u00020\u000fH\u0016J4\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001c"}, d2 = {"Lcom/renteasy/app/data/repository/FirebaseBookingRepository;", "Lcom/renteasy/app/domain/repository/BookingRepository;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "(Lcom/google/firebase/firestore/FirebaseFirestore;)V", "createBooking", "Lkotlin/Result;", "", "booking", "Lcom/renteasy/app/domain/model/Booking;", "createBooking-gIAlu-s", "(Lcom/renteasy/app/domain/model/Booking;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBookingById", "Lkotlinx/coroutines/flow/Flow;", "id", "", "getBookingsByOwner", "", "ownerId", "getBookingsByRenter", "renterId", "updateBookingStatus", "status", "Lcom/renteasy/app/domain/model/BookingStatus;", "paymentStatus", "Lcom/renteasy/app/domain/model/PaymentStatus;", "updateBookingStatus-BWLJW6A", "(Ljava/lang/String;Lcom/renteasy/app/domain/model/BookingStatus;Lcom/renteasy/app/domain/model/PaymentStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class FirebaseBookingRepository implements com.renteasy.app.domain.repository.BookingRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    
    @javax.inject.Inject()
    public FirebaseBookingRepository(@org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.FirebaseFirestore firestore) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.renteasy.app.domain.model.Booking>> getBookingsByRenter(@org.jetbrains.annotations.NotNull()
    java.lang.String renterId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.renteasy.app.domain.model.Booking>> getBookingsByOwner(@org.jetbrains.annotations.NotNull()
    java.lang.String ownerId) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.renteasy.app.domain.model.Booking> getBookingById(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
        return null;
    }
}