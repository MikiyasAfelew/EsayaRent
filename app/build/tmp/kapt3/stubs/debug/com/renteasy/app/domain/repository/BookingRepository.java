package com.renteasy.app.domain.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0018\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\n2\u0006\u0010\u000b\u001a\u00020\fH&J\u001c\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u000e0\n2\u0006\u0010\u000f\u001a\u00020\fH&J\u001c\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u000e0\n2\u0006\u0010\u0011\u001a\u00020\fH&J4\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u00a6@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0019"}, d2 = {"Lcom/renteasy/app/domain/repository/BookingRepository;", "", "createBooking", "Lkotlin/Result;", "", "booking", "Lcom/renteasy/app/domain/model/Booking;", "createBooking-gIAlu-s", "(Lcom/renteasy/app/domain/model/Booking;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBookingById", "Lkotlinx/coroutines/flow/Flow;", "id", "", "getBookingsByOwner", "", "ownerId", "getBookingsByRenter", "renterId", "updateBookingStatus", "status", "Lcom/renteasy/app/domain/model/BookingStatus;", "paymentStatus", "Lcom/renteasy/app/domain/model/PaymentStatus;", "updateBookingStatus-BWLJW6A", "(Ljava/lang/String;Lcom/renteasy/app/domain/model/BookingStatus;Lcom/renteasy/app/domain/model/PaymentStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface BookingRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.renteasy.app.domain.model.Booking>> getBookingsByRenter(@org.jetbrains.annotations.NotNull()
    java.lang.String renterId);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.renteasy.app.domain.model.Booking>> getBookingsByOwner(@org.jetbrains.annotations.NotNull()
    java.lang.String ownerId);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.renteasy.app.domain.model.Booking> getBookingById(@org.jetbrains.annotations.NotNull()
    java.lang.String id);
}