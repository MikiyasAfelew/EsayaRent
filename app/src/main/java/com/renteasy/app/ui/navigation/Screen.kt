package com.renteasy.app.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register") // renamed from create_account
    
    object Home : Screen("home")
    object Map : Screen("map")
    object Bookings : Screen("bookings")
    object Profile : Screen("profile")
    
    object Chat : Screen("chat/{ownerId}?ownerName={ownerName}&propertyTitle={propertyTitle}&propertyPrice={propertyPrice}&propertyImage={propertyImage}") {
        fun createRoute(
            ownerId: String,
            ownerName: String = "Support",
            propertyTitle: String = "RentEasy Support",
            propertyPrice: String = "-",
            propertyImage: String = ""
        ): String {
            val encName = java.net.URLEncoder.encode(ownerName, "UTF-8")
            val encTitle = java.net.URLEncoder.encode(propertyTitle, "UTF-8")
            val encPrice = java.net.URLEncoder.encode(propertyPrice, "UTF-8")
            val encImage = java.net.URLEncoder.encode(propertyImage, "UTF-8")
            val safeOwnerId = if (ownerId.isBlank()) "support" else ownerId
            return "chat/$safeOwnerId?ownerName=$encName&propertyTitle=$encTitle&propertyPrice=$encPrice&propertyImage=$encImage"
        }
    }
    
    object PropertyDetail : Screen("property_detail/{propertyId}") {
        fun createRoute(propertyId: String) = "property_detail/$propertyId"
    }
    
    object OwnerDashboard : Screen("owner_dashboard")
    object AddProperty : Screen("add_property")
    
    object IdentityVerification : Screen("identity_verification")
    object VerificationStatus : Screen("verification_status")
    
    object MyListings : Screen("my_listings")
    object EditProfile : Screen("edit_profile")
    object PaymentMethods : Screen("payment_methods")
    object SavedHomes : Screen("saved_homes")
    object PriceAlerts : Screen("price_alerts")
    object AboutSupport : Screen("about_support")
    
    object Checkout : Screen("checkout/{propertyId}") {
        fun createRoute(propertyId: String) = "checkout/$propertyId"
    }
}
