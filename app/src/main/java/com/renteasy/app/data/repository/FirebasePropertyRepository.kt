package com.renteasy.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.renteasy.app.domain.model.*
import com.renteasy.app.domain.repository.PropertyRepository
import com.renteasy.app.data.dto.PropertyDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context


@Singleton
class FirebasePropertyRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    @ApplicationContext private val context: Context
) : PropertyRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val snapshot = firestore.collection("properties").limit(1).get().await()
                if (snapshot.isEmpty) {
                    val samples = listOf(
                        Property(
                            id = "1",
                            title = "Luxury Villa in Bole",
                            location = "Bole, Addis Ababa",
                            district = "Bole",
                            pricePerMonth = 45000,
                            status = PropertyStatus.APPROVED,
                            isVerified = true,
                            images = listOf("https://images.unsplash.com/photo-1613490493576-7fde63acd811?auto=format&fit=crop&q=80&w=800"),
                            category = PropertyCategory.HOUSE,
                            bedrooms = 4,
                            bathrooms = 3,
                            areaM2 = 350,
                            description = "A magnificent luxury villa located in the heart of Bole. Features modern architecture, a spacious garden, and high-end finishes.",
                            ownerId = "1",
                            ownerName = "Abebe Kebede",
                            ownerRating = 4.8f,
                            createdAt = System.currentTimeMillis() - 86400000
                        ),
                        Property(
                            id = "2",
                            title = "Modern Studio Kazanchis",
                            location = "Kazanchis, Addis Ababa",
                            district = "Kazanchis",
                            pricePerMonth = 12500,
                            status = PropertyStatus.APPROVED,
                            isVerified = false,
                            images = listOf("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&q=80&w=800"),
                            category = PropertyCategory.APARTMENT,
                            bedrooms = 1,
                            bathrooms = 1,
                            areaM2 = 45,
                            description = "Cozy and modern studio apartment perfect for young professionals. Located within walking distance to major offices.",
                            ownerId = "2",
                            ownerName = "Sara Tekle",
                            ownerRating = 4.5f,
                            createdAt = System.currentTimeMillis() - 172800000
                        ),
                        Property(
                            id = "3",
                            title = "Spacious Office, Sarbet",
                            location = "Sarbet, Addis Ababa",
                            district = "Sarbet",
                            pricePerMonth = 18000,
                            status = PropertyStatus.APPROVED,
                            isVerified = true,
                            images = listOf("https://images.unsplash.com/photo-1497366216548-37526070297c?auto=format&fit=crop&q=80&w=800"),
                            category = PropertyCategory.OFFICE,
                            rooms = 3,
                            areaM2 = 120,
                            description = "Professional office space in a secure building. Ideal for startups or small firms.",
                            ownerId = "3",
                            ownerName = "Dawit Girma",
                            ownerRating = 4.2f,
                            createdAt = System.currentTimeMillis() - 43200000
                        )
                    )
                    for (p in samples) {
                        firestore.collection("properties").document(p.id).set(PropertyDto.fromDomain(p)).await()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getProperties(): Flow<List<Property>> = callbackFlow {
        val subscription = firestore.collection("properties")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    android.util.Log.e("FirebasePropertyRepo", "getProperties error: ${error.message}", error)
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val properties = snapshot.toObjects(PropertyDto::class.java).mapNotNull { dto ->
                        try { dto.toDomain() } catch (e: Exception) { null }
                    }.filter { 
                        it.status == PropertyStatus.APPROVED || 
                        it.status == PropertyStatus.AVAILABLE || 
                        it.status == PropertyStatus.RESERVED || 
                        it.status == PropertyStatus.RENTED 
                    }.sortedByDescending { it.createdAt }
                    trySend(properties)
                }
            }
        awaitClose { subscription.remove() }
    }

    override fun getPropertiesByStatus(status: PropertyStatus): Flow<List<Property>> = callbackFlow {
        // NOTE: We intentionally avoid .orderBy() here because Firestore requires a composite
        // index for whereEqualTo+orderBy on different fields. Without the index deployed to the
        // Firestore console, the query silently returns an empty snapshot for authenticated users.
        // Sorting is done in-memory instead.
        val subscription = firestore.collection("properties")
            .whereEqualTo("status", status.name)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    android.util.Log.e("FirebasePropertyRepo", "getPropertiesByStatus error: ${error.message}", error)
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val properties = snapshot.toObjects(PropertyDto::class.java).mapNotNull { dto ->
                        try { dto.toDomain() } catch (e: Exception) { null }
                    }.sortedByDescending { it.createdAt }
                    trySend(properties)
                }
            }
        awaitClose { subscription.remove() }
    }

    override fun getPropertyById(id: String): Flow<Property?> = callbackFlow {
        val subscription = firestore.collection("properties").document(id)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(null)
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    try {
                        trySend(snapshot.toObject(PropertyDto::class.java)?.toDomain())
                    } catch (e: Exception) {
                        trySend(null)
                    }
                } else {
                    trySend(null)
                }
            }
        awaitClose { subscription.remove() }
    }

    private val storage = FirebaseStorage.getInstance()

    /** Upload a content:// URI to Firebase Storage and return the https download URL. */
    private suspend fun uploadImageToStorage(uriStr: String, storagePath: String): String {
        val uri = android.net.Uri.parse(uriStr)
        val ref = storage.reference.child(storagePath)
        // Use putStream via ContentResolver — more reliable than putFile with content:// URIs
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw Exception("Cannot read selected image")
        ref.putStream(inputStream).await()
        inputStream.close()
        return ref.downloadUrl.await().toString()
    }

    private fun getFallbackStockImage(category: PropertyCategory, index: Int): String {
        val houseImages = listOf(
            "https://images.unsplash.com/photo-1560518883-ce09059eeffa?auto=format&fit=crop&q=80&w=800",
            "https://images.unsplash.com/photo-1613490493576-7fde63acd811?auto=format&fit=crop&q=80&w=800",
            "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&fit=crop&q=80&w=800"
        )
        val apartmentImages = listOf(
            "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&q=80&w=800",
            "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&q=80&w=800",
            "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?auto=format&fit=crop&q=80&w=800"
        )
        val officeImages = listOf(
            "https://images.unsplash.com/photo-1497366216548-37526070297c?auto=format&fit=crop&q=80&w=800",
            "https://images.unsplash.com/photo-1497215728101-856f4ea42174?auto=format&fit=crop&q=80&w=800"
        )
        val list = when (category) {
            PropertyCategory.HOUSE -> houseImages
            PropertyCategory.APARTMENT -> apartmentImages
            PropertyCategory.OFFICE -> officeImages
            PropertyCategory.GUEST -> houseImages
        }
        return list.getOrElse(index % list.size) { list[0] }
    }

    private fun getBase64ImageFallback(uriStr: String): String {
        return try {
            val uri = android.net.Uri.parse(uriStr)
            val maxDimension = 600
            val resolver = context.contentResolver
            val options = android.graphics.BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            resolver.openInputStream(uri)?.use { input ->
                android.graphics.BitmapFactory.decodeStream(input, null, options)
            }
            var inSampleSize = 1
            val srcWidth = options.outWidth
            val srcHeight = options.outHeight
            if (srcWidth > maxDimension || srcHeight > maxDimension) {
                val halfWidth = srcWidth / 2
                val halfHeight = srcHeight / 2
                while ((halfWidth / inSampleSize) >= maxDimension && (halfHeight / inSampleSize) >= maxDimension) {
                    inSampleSize *= 2
                }
            }
            val decodeOptions = android.graphics.BitmapFactory.Options().apply {
                this.inSampleSize = inSampleSize
            }
            val bitmap = resolver.openInputStream(uri)?.use { input ->
                android.graphics.BitmapFactory.decodeStream(input, null, decodeOptions)
            } ?: return ""
            val scaledBitmap = if (bitmap.width > maxDimension || bitmap.height > maxDimension) {
                val scale = maxDimension.toFloat() / maxOf(bitmap.width, bitmap.height)
                android.graphics.Bitmap.createScaledBitmap(
                    bitmap,
                    (bitmap.width * scale).toInt(),
                    (bitmap.height * scale).toInt(),
                    true
                )
            } else bitmap
            val baos = java.io.ByteArrayOutputStream()
            scaledBitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 70, baos)
            val baosBytes = baos.toByteArray()
            val base64 = android.util.Base64.encodeToString(baosBytes, android.util.Base64.NO_WRAP)
            "data:image/jpeg;base64,$base64"
        } catch (e: Exception) {
            android.util.Log.e("FirebasePropertyRepo", "Base64 fallback failed for $uriStr: ${e.message}", e)
            ""
        }
    }

    override suspend fun addProperty(property: Property): Result<Unit> {
        return try {
            val uploadedImages = property.images.mapIndexed { index, img ->
                if (img.startsWith("content://") || img.startsWith("file:/") || !img.startsWith("http")) {
                    try {
                        // Upload to Firebase Storage: properties/{propertyId}/{index}.jpg
                        uploadImageToStorage(img, "properties/${property.id}/$index.jpg")
                    } catch (e: Exception) {
                        android.util.Log.e("FirebasePropertyRepo", "Firebase Storage upload failed: ${e.message}. Attempting Base64 fallback.", e)
                        val base64 = getBase64ImageFallback(img)
                        if (base64.isNotEmpty()) {
                            base64
                        } else {
                            getFallbackStockImage(property.category, index)
                        }
                    }
                } else {
                    img // already remote HTTPS URL
                }
            }
            val propertyWithUrls = property.copy(images = uploadedImages)
            val dto = PropertyDto.fromDomain(propertyWithUrls)
            firestore.collection("properties").document(property.id).set(dto).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePropertyStatus(id: String, status: PropertyStatus): Result<Unit> {
        return try {
            firestore.collection("properties").document(id).update("status", status.name).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getPropertiesByOwner(ownerId: String): Flow<List<Property>> = callbackFlow {
        val subscription = firestore.collection("properties")
            .whereEqualTo("ownerId", ownerId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val properties = snapshot.toObjects(PropertyDto::class.java).mapNotNull { dto ->
                        try { dto.toDomain() } catch (e: Exception) { null }
                    }.sortedByDescending { it.createdAt }
                    trySend(properties)
                }
            }
        awaitClose { subscription.remove() }
    }

    override suspend fun deleteProperty(id: String): Result<Unit> {
        return try {
            firestore.collection("properties").document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProperty(property: Property): Result<Unit> {
        return try {
            val updates = mapOf<String, Any?>(
                "title" to property.title,
                "location" to property.location,
                "district" to property.district,
                "pricePerMonth" to property.pricePerMonth,
                "description" to property.description,
                "bedrooms" to property.bedrooms,
                "bathrooms" to property.bathrooms,
                "areaM2" to property.areaM2,
                "category" to property.category.name
            )
            firestore.collection("properties").document(property.id).update(updates).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

