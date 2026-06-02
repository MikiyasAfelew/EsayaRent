package com.renteasy.app.data.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject

object SupportEmailSender {
    // Paste your Web3Forms access key for stoicrypt@gmail.com here.
    // To get a free key, enter your email on https://web3forms.com
    private const val WEB3FORMS_ACCESS_KEY = "68180725-8043-40d1-8cda-40d7ccb7b907"

    /**
     * Sends an email in the background to the support team using Web3Forms.
     * Web3Forms is a free, zero-config transactional contact form processor.
     */
    suspend fun sendEmail(
        userEmail: String,
        subject: String,
        message: String,
        userId: String
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://api.web3forms.com/submit")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json; utf-8")
            conn.setRequestProperty("Accept", "application/json")
            conn.doOutput = true

            // Set up post payload
            val jsonBody = JSONObject().apply {
                put("access_key", WEB3FORMS_ACCESS_KEY)
                put("from_name", "RentEasy Support")
                put("replyto", userEmail)
                put("name", "RentEasy App User")
                put("email", userEmail)
                put("subject", "RentEasy Support: $subject")
                put("message", "User Details:\n- Email: $userEmail\n- ID: $userId\n\nMessage Body:\n$message")
            }

            conn.outputStream.use { os ->
                OutputStreamWriter(os, "UTF-8").use { writer ->
                    writer.write(jsonBody.toString())
                    writer.flush()
                }
            }

            val responseCode = conn.responseCode
            if (responseCode in 200..299) {
                Result.success(Unit)
            } else {
                val errorStream = conn.errorStream?.bufferedReader()?.readText() ?: ""
                Result.failure(Exception("Server returned status $responseCode: $errorStream"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
