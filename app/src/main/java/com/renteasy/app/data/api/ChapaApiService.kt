package com.renteasy.app.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.GET
import retrofit2.http.Path

interface ChapaApiService {

    @POST("v1/transaction/initialize")
    suspend fun initializeTransaction(
        @Header("Authorization") authorization: String,
        @Body request: ChapaInitializeRequest
    ): Response<ChapaInitializeResponse>

    @GET("v1/transaction/verify/{tx_ref}")
    suspend fun verifyTransaction(
        @Header("Authorization") authorization: String,
        @Path("tx_ref") txRef: String
    ): Response<ChapaVerifyResponse>

    companion object {
        private const val BASE_URL = "https://api.chapa.co/"

        fun create(): ChapaApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ChapaApiService::class.java)
        }
    }
}

data class ChapaInitializeRequest(
    val amount: Double,
    val currency: String = "ETB",
    val email: String,
    val first_name: String,
    val last_name: String,
    val tx_ref: String,
    val callback_url: String? = null,
    val return_url: String? = null,
    val customization: ChapaCustomization? = null
)

data class ChapaCustomization(
    val title: String?,
    val description: String?
)

data class ChapaInitializeResponse(
    val message: String?,
    val status: String?,
    val data: ChapaData?
)

data class ChapaData(
    val checkout_url: String?
)

data class ChapaVerifyResponse(
    val message: String?,
    val status: String?,
    val data: ChapaVerifyData?
)

data class ChapaVerifyData(
    val status: String?,
    val tx_ref: String?,
    val amount: Double?
)
