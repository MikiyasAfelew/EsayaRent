package com.renteasy.app.data.dto

import com.renteasy.app.domain.model.*

data class PropertyDto(
    val id: String = "",
    val title: String = "",
    val location: String = "",
    val district: String = "",
    val pricePerMonth: Int = 0,
    val currency: String = "ETB",
    val isVerified: Boolean = false,
    val status: String = "PENDING",
    val images: List<String> = emptyList(),
    val category: String = "HOUSE",
    val bedrooms: Int = 0,
    val bathrooms: Int = 0,
    val areaM2: Int = 0,
    val rooms: Int = 0,
    val floorNumber: Int = 0,
    val hasParkingSpace: Boolean = false,
    val description: String = "",
    val amenities: List<AmenityDto> = emptyList(),
    val ownerId: String = "",
    val ownerName: String = "",
    val ownerRating: Float = 0f,
    val ownerAvatarUrl: String? = null,
    val occupancyRate: Int = 0,
    val createdAt: Long = 0
) {
    fun toDomain(): Property = Property(
        id = id,
        title = title,
        location = location,
        district = district,
        pricePerMonth = pricePerMonth,
        currency = currency,
        isVerified = isVerified,
        status = try { PropertyStatus.valueOf(status) } catch (e: Exception) { PropertyStatus.PENDING },
        images = images.mapIndexed { index, img ->
            if (img.startsWith("content://") || img.startsWith("file:/")) {
                val resolvedCat = try { PropertyCategory.valueOf(category) } catch (e: Exception) { PropertyCategory.HOUSE }
                val list = when (resolvedCat) {
                    PropertyCategory.APARTMENT -> listOf(
                        "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&q=80&w=800",
                        "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&q=80&w=800",
                        "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?auto=format&fit=crop&q=80&w=800"
                    )
                    PropertyCategory.HOUSE -> listOf(
                        "https://images.unsplash.com/photo-1580587771525-78b9dba3b914?auto=format&fit=crop&q=80&w=800",
                        "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&q=80&w=800",
                        "https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?auto=format&fit=crop&q=80&w=800"
                    )
                    PropertyCategory.GUEST -> listOf(
                        "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&q=80&w=800",
                        "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?auto=format&fit=crop&q=80&w=800"
                    )
                    PropertyCategory.OFFICE -> listOf(
                        "https://images.unsplash.com/photo-1497366216548-37526070297c?auto=format&fit=crop&q=80&w=800",
                        "https://images.unsplash.com/photo-1497215728101-856f4ea42174?auto=format&fit=crop&q=80&w=800"
                    )
                }
                list.getOrElse(index % list.size) { list[0] }
            } else {
                img
            }
        },
        category = try { PropertyCategory.valueOf(category) } catch (e: Exception) { PropertyCategory.HOUSE },
        bedrooms = bedrooms,
        bathrooms = bathrooms,
        areaM2 = areaM2,
        rooms = rooms,
        floorNumber = floorNumber,
        hasParkingSpace = hasParkingSpace,
        description = description,
        amenities = amenities.map { it.toDomain() },
        ownerId = ownerId,
        ownerName = ownerName,
        ownerRating = ownerRating,
        ownerAvatarUrl = ownerAvatarUrl,
        occupancyRate = occupancyRate,
        createdAt = createdAt
    )

    companion object {
        fun fromDomain(p: Property) = PropertyDto(
            id = p.id,
            title = p.title,
            location = p.location,
            district = p.district,
            pricePerMonth = p.pricePerMonth,
            currency = p.currency,
            isVerified = p.isVerified,
            status = p.status.name,
            images = p.images,
            category = p.category.name,
            bedrooms = p.bedrooms,
            bathrooms = p.bathrooms,
            areaM2 = p.areaM2,
            rooms = p.rooms,
            floorNumber = p.floorNumber,
            hasParkingSpace = p.hasParkingSpace,
            description = p.description,
            amenities = p.amenities.map { AmenityDto.fromDomain(it) },
            ownerId = p.ownerId,
            ownerName = p.ownerName,
            ownerRating = p.ownerRating,
            ownerAvatarUrl = p.ownerAvatarUrl,
            occupancyRate = p.occupancyRate,
            createdAt = p.createdAt
        )
    }
}

data class AmenityDto(
    val icon: String = "WIFI",
    val label: String = ""
) {
    fun toDomain(): Amenity = Amenity(
        icon = try { AmenityIcon.valueOf(icon) } catch (e: Exception) { AmenityIcon.WIFI },
        label = label
    )
    companion object {
        fun fromDomain(a: Amenity) = AmenityDto(
            icon = a.icon.name,
            label = a.label
        )
    }
}
