package com.renteasy.app.domain.repository

import com.renteasy.app.domain.model.Property
import kotlinx.coroutines.flow.Flow

interface PropertyRepository {
    fun getProperties(): Flow<List<Property>>
    fun getPropertiesByStatus(status: com.renteasy.app.domain.model.PropertyStatus): Flow<List<Property>>
    fun getPropertyById(id: String): Flow<Property?>
    fun getPropertiesByOwner(ownerId: String): Flow<List<Property>>
    suspend fun addProperty(property: Property): Result<Unit>
    suspend fun updatePropertyStatus(id: String, status: com.renteasy.app.domain.model.PropertyStatus): Result<Unit>
    suspend fun deleteProperty(id: String): Result<Unit>
    suspend fun updateProperty(property: Property): Result<Unit>
}
