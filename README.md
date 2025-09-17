# Mobile Treasure Hunt (Scavenger Hunt App)

**Final Portfolio Project -- Oregon State University (CS 492: Mobile
Software Development)**\
📅 Completed: March 2024

Mobile Treasure Hunt is an Android app built with **Kotlin** and
**Jetpack Compose** that turns the classic treasure hunt game into a
GPS-powered mobile experience. Players follow location-based clues,
check in at real-world destinations, and progress through the hunt until
they find the "treasure."

This project demonstrates my ability to design and develop
**location-aware mobile applications**, integrating maps, permissions,
asynchronous programming, and Jetpack Compose UI patterns.

------------------------------------------------------------------------

## 🔑 Key Features

-   **Location-Based Gameplay**
    -   Each clue is tied to a real-world GPS coordinate
    -   Location accuracy verified with the **Haversine formula**
-   **Game Flow**
    -   Start screen with title, rules, and a scrollable introduction
    -   Dynamic **Clue Screen** with:
        -   Text clues & hints
        -   Animated timer
        -   "Found It!" validation button
    -   **Found Screen** for solved clues with educational info and a
        continue button
    -   **Completion Screen** showing total elapsed time and a
        congratulatory message
-   **Permissions & Safety**
    -   Initial permissions screen to request location and system
        access
    -   Error handling for invalid or inaccessible locations
-   **Structure & Best Practices**
    -   MVVM with `ViewModel` + `StateFlow`
    -   Modular UI with Jetpack Compose components
    -   Organized by packages: `ui`, `data`, `model`

------------------------------------------------------------------------

## 🛠️ Technology Stack

-   **Language:** Kotlin
-   **UI:** Jetpack Compose
-   **Architecture:** MVVM (ViewModel + StateFlow)
-   **Location:** Android Location Services + Haversine formula
-   **Tools:** Android Studio, Emulator (mock GPS), Gradle

------------------------------------------------------------------------

## 📂 Project Structure

    com.example.scavengerhuntapp
    │
    ├── MainActivity.kt           # Entry point
    ├── ScavengerHuntApp.kt       # App container with navigation
    ├── LocationServices.kt       # GPS + Haversine validation
    ├── Haversine.kt              # Distance calculation utility
    │
    ├── ui                       # Jetpack Compose screens
    │   ├── StartScreen.kt
    │   ├── ClueScreen.kt
    │   ├── FoundScreen.kt
    │   ├── Components.kt
    │   └── theme
    │
    ├── model                    # Data models for Clues, Game state
    └── data                     # Resource-backed clue data

------------------------------------------------------------------------

## 🎓 What I Learned

-   Building **location-aware Android apps** with GPS services
-   Managing permissions for sensitive services (location, storage)
-   Using **Haversine formula** to validate geofences
-   Structuring Android apps with **MVVM & StateFlow**
-   Designing responsive UIs with **Jetpack Compose**
-   Testing and debugging with **Android Emulator mock locations**

------------------------------------------------------------------------

## 📸 Demo

-   **Video Walkthrough:** Demonstrates app running on Android Emulator
-   **Clue Progression:** Start → Clue → Found → Completion flow
-   **Repository:** \[GitHub Link\]

------------------------------------------------------------------------

👉 This project was designed as a **portfolio assignment** to showcase
mobile development proficiency, with emphasis on GPS services, Compose
UI, and Android best practices.
