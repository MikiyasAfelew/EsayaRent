package com.renteasy.app.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.renteasy.app.data.repository.FirebaseAuthRepository
import com.renteasy.app.data.repository.FirebasePropertyRepository
import com.renteasy.app.data.repository.FirebaseBookingRepository
import com.renteasy.app.domain.repository.BookingRepository
import com.renteasy.app.domain.repository.AuthRepository
import com.renteasy.app.domain.repository.PropertyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        firebaseAuthRepository: FirebaseAuthRepository
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindPropertyRepository(
        firebasePropertyRepository: FirebasePropertyRepository
    ): PropertyRepository

    @Binds
    @Singleton
    abstract fun bindBookingRepository(
        firebaseBookingRepository: FirebaseBookingRepository
    ): BookingRepository
}

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore
}
