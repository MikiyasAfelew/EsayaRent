 Team Members

| Full Name        | Student ID |
| ---------------- | ---------- |
| Muaz Muhammed    | DBU1501382 |
| Nahusenay Simeng | DBU1501390 |
| Halid Faruk      | DBU1501642 |
| Mikiyas Aflew    | DBU1501374 |
| Kidus Misaw      | DBU1501305 |






# RentEasy 🏠

RentEasy is a modern, premium, and fully featured Android application designed to bridge the gap between renters and property owners. Built with modern Android development guidelines, Jetpack Compose, Clean Architecture, Hilt Dependency Injection, and Firebase services, it delivers an optimized, smooth, and robust mobile experience.

---

## 🌟 Key Features

### 👤 User Roles
*   **Renters**: Discover properties, view interactive maps, save properties, book visits/rentals, and pay via Telebirr or Chapa.
*   **Owners**: Manage listings, view dashboard analytics (earnings, active vs pending stats), approve/reject booking requests, and upload verification credentials.

### 🏢 Property Discovery & Alerts
*   **Home Dashboard**: Browse properties by category, search by location/specs, and apply advanced filters.
*   **Interactive Maps**: View property placements geographically to find the perfect location.
*   **Saved Homes**: Bookmark and easily access favorite property listings.
*   **Price Alerts**: Set real-time alerts for specific districts (e.g., Bole, Kazanchis, Sarbet) with maximum budget caps.

### 💳 Modern Payments & Checkout
*   **Telebirr & Chapa Integrations**: Native payment gateway integration for secure rent and reservation payments.
*   **Auto-Calculated Receipts**: Generates instant transactional details, including system fees (5% commission rate).

### 💬 Real-Time Communication
*   **In-App Chat**: Directly message owners and renters to discuss rentals, pricing, or coordinate visits.

### 🛡️ Identity Verification
*   **Multi-Step Verification**: ID Upload (front & back), Liveness Selfie verification, and Property Ownership Proof (required for owners).
*   **Status Tracking**: Real-time review status updates (Pending, Verified, Rejected).

---

## 🏗️ Technical Stack & Architecture

RentEasy is developed using standard MVVM architecture combined with modern Android libraries:

*   **Core**: Kotlin & Jetpack Compose (Declarative UI)
*   **Theme**: Material 3 UI design system with dynamic light/dark mode adaptation
*   **DI Engine**: Dagger Hilt
*   **Local Caching**: Room Database
*   **Remote Backend**: Firebase (Authentication, Cloud Firestore, Firebase Storage)
*   **API Network**: Retrofit 2 & Gson
*   **Image Loading**: Coil Image Loader
*   **Payment Gateway**: Chapa SDK / Telebirr APIs

---

## 📂 Project Structure

```
app/src/main/java/com/renteasy/app
│
├── data/                  # Repository implementations, DTOs, Local Room DB, API interfaces
├── domain/                # Use Cases, Repository abstractions, domain entities/models
├── di/                    # Dagger Hilt dependency modules
└── ui/
    ├── components/        # Reusable Compose components (buttons, nav bars, cards)
    ├── navigation/        # Jetpack Compose Navigation Graph and ViewModels
    ├── theme/             # Color scheme, typography, shape tokens, theme controller
    └── screens/           # Feature UI Screen modules:
        ├── alerts/        # Price alerts UI
        ├── auth/          # Authentication & Registration screens
        ├── bookings/      # User & Owner booking tabs & receipts
        ├── chat/          # Messaging flows
        ├── checkout/      # Telebirr payment checkout & receipts
        ├── detail/        # Property Detail page & ViewModel
        ├── home/          # Search & Feed screen
        ├── map/           # Interactive Map screen
        ├── owner/         # Owner dashboard & Property listing management
        ├── profile/       # Profile, settings, payment methods, support
        └── verification/  # Identity and ownership verification flows
```

---

## 🚀 Getting Started

### Prerequisites
*   Android Studio Jellyfish / Koala or newer
*   JDK 17
*   A Firebase project configured for Android (`google-services.json` placed in the `app/` directory)

### Configuration

#### 1. Setup Firebase Configuration
Make sure your `google-services.json` file is present in the `app` directory:
```bash
app/google-services.json
```

#### 2. Set Up local.properties Credentials
RentEasy utilizes secure payment gateways. To configure the SDK, add your credentials to the `local.properties` file:
```properties
CHAPA_SECRET_KEY=your_chapa_secret_key_here
```

### Build & Run
To compile and run the application locally on an emulator or physical device:
1. Open the project root directory in Android Studio.
2. Let Gradle sync complete.
3. Build the project using:
```bash
./gradlew assembleDebug
```
4. Run the debug build on your connected device.

---

## 🧪 Testing

To run the unit tests and instrumentation tests:

```bash
# Run unit tests
./gradlew testDebugUnitTest

# Run Android instrumentation tests
./gradlew connectedAndroidTest
```
