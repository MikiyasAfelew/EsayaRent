package com.renteasy.app.domain.model

enum class PropertyStatus {
    PENDING, APPROVED, REJECTED, AVAILABLE, RESERVED, RENTED
}

data class Property(
    val id: String,
    val title: String,
    val location: String,
    val district: String,
    val pricePerMonth: Int,
    val currency: String = "ETB",
    val isVerified: Boolean = false,
    val status: PropertyStatus = PropertyStatus.PENDING,
    val images: List<String>,      // URL list
    val category: PropertyCategory,
    val bedrooms: Int = 0,
    val bathrooms: Int = 0,
    val areaM2: Int = 0,
    val rooms: Int = 0,
    val floorNumber: Int = 0,
    val hasParkingSpace: Boolean = false,
    val description: String = "",
    val amenities: List<Amenity> = emptyList(),
    val ownerId: String = "",
    val ownerName: String = "",
    val ownerRating: Float = 0f,
    val ownerAvatarUrl: String? = null,
    val occupancyRate: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

enum class PropertyCategory(val label: String) {
    HOUSE("House"),
    APARTMENT("Apartment"),
    OFFICE("Office"),
    GUEST("Guest")
}

data class Amenity(val icon: AmenityIcon, val label: String)

enum class AmenityIcon {
    WIFI, WATER, PARKING, SECURITY, GYM, POOL, FIBER_INTERNET, POWER_BACKUP, ROOF_TERRACE, SECURED_SLOTS
}
