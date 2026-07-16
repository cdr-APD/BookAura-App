# BookAura (Book Library) 📚✨

Welcome to **BookAura**, a premium book tracking and discovery application built for Android. With its modern dark-themed aesthetic, dynamic search integrations, and personalized reading progress tracking, BookAura elevates the digital reading companion experience.

---

## 🎨 Visual Design & Aesthetics

BookAura is styled with a custom-crafted design system, prioritizing visual excellence and smooth interactions:
*   **Atmospheric Theme:** Built on a premium, deep-navy and slate-purple gradient backdrop (`BackgroundDarkStart` ➔ `BackgroundDarkCenter` ➔ `BackgroundDarkEnd`) rather than standard flat black.
*   **Card Components:** Uses sleek container backdrops (`CardBackground` / `#1E2235`) with subtle borders for high-fidelity readability.
*   **Typography:** Elegant text hierarchy featuring clean, responsive type configurations suited for immersive content scanning.
*   **Micro-Animations & Feedback:** Subtle state transitions, category pill selectors, interactive rating stars, and interactive progress bars that respond seamlessly to user updates.

---

## 🚀 Key Features

*   **🏠 Personalized Home Dashboard:**
    *   Dynamic greeting card.
    *   **In-Progress Tracker:** Tracks the book you are currently reading, showing chapter progress, a visual progress bar, and a quick one-tap button to increment reading progress.
    *   **All Books Directory:** Displays a grid containing your local catalog.
*   **🔍 Explore & Discover:**
    *   **Google Books API Integration:** Instant online catalog search querying millions of volumes.
    *   **Category Filtering:** Quick-toggle genre chips (Fantasy, Sci-Fi, Finance, Self Help, Mystery, Action).
    *   **Advanced Sorting:** Sort search and library results dynamically by Title (A-Z, Z-A) or Rating (High-to-Low, Low-to-High).
*   **📖 Immersive Book Details:**
    *   Shows high-quality book covers, ratings, publisher metadata, and full book descriptions.
    *   **Chrome Custom Tabs:** Instantly read book previews in-app via a high-performance integrated browser window.
    *   **Bookmark Toggle:** Quick saving and unsaving to update your personal shelf.
*   **💾 Smart Saved Library:**
    *   Automatically separates saved books into **Currently Reading** (active progress) and **Unread / Backlog** collections.
*   **👤 User Profile & Statistics:**
    *   Summarizes user info (membership status, name, avatar).
    *   Calculates real-time stats including total books saved, currently reading counts, and average book ratings.

---

## 🏗️ Architecture & Package Structure

The codebase is organized following clean architecture principles, separating concern layers logically:

```
com.example.booklibrary
 ├── data
 │    ├── model              # Domain data classes (e.g., Book.kt)
 │    └── repository         # Local (in-memory flow) & Remote (Google Books API) data layers
 ├── navigation              # NavGraph setup, screen routes, and BottomNavBar wiring
 ├── network
 │    ├── api                # Retrofit BookApiService endpoint declarations
 │    ├── dto                # Google Books API Response DTOs
 │    ├── mapper             # Maps DTO models to Domain/UI models
 │    └── retrofit           # Retrofit client configurations & ApiKeyInterceptor
 ├── ui
 │    ├── components         # Reusable widgets (BookCard, SearchBar, GenreChip, SavedBookCard, etc.)
 │    ├── screens            # Full-page Composables (Home, Explore, BookDetails, Saved, Profile)
 │    └── theme              # Design tokens (Color.kt, Theme.kt, Type.kt)
 └── viewmodel               # State management using ViewModel & Kotlin StateFlows
```

---

## 🛠️ Technology Stack

BookAura leverages the modern Android Jetpack library suite:
*   **Language:** [Kotlin](https://kotlinlang.org/) (version `2.2.10`)
*   **UI Framework:** [Jetpack Compose](https://developer.android.com/compose) with standard [Kotlin Compose Compiler](https://developer.android.com/develop/ui/compose/compiler)
*   **Design System:** [Material 3](https://m3.material.io/)
*   **Networking:** [Retrofit 2](https://square.github.io/retrofit/) & [OkHttp3](https://square.github.io/okhttp/) with Logging Interceptor
*   **Image Loading:** [Coil Compose](https://coil-kt.github.io/coil/compose/) for async cover fetching and memory-cached rendering
*   **Navigation:** [Jetpack Navigation Compose](https://developer.android.com/develop/ui/compose/navigation) (`2.9.3`)
*   **Web Previews:** [AndroidX Browser (Chrome Custom Tabs)](https://developer.android.com/browser/custom-tabs)

---

## ⚡ Setup & Run Instructions

### 1. Prerequisites
*   **Android Studio** (Koala / Ladybug or newer recommended)
*   **Android SDK 24+** (Compiled against SDK **36**)
*   **Google Books API Key:** Required for searching books online.

### 2. Configure Google Books API Key
To connect search queries to the Google Books API:
1.  Go to the [Google Cloud Console](https://console.cloud.google.com/).
2.  Enable the **Books API**.
3.  Generate an **API key** under the *Credentials* tab.
4.  Open the project root directory and locate or create `local.properties`.
5.  Add your API key to `local.properties` as follows:
    ```properties
    GOOGLE_BOOKS_API_KEY=your_actual_api_key_here
    ```
*(Note: The build configuration automatically reads this variable and generates a `BuildConfig.GOOGLE_BOOKS_API_KEY` wrapper, which is appended to network requests using the `ApiKeyInterceptor`.)*

### 3. Build & Run
1.  Import the project into Android Studio.
2.  Let Gradle sync finish successfully.
3.  Select a physical device or emulator running **API 24 or higher**.
4.  Click **Run** (`Shift + F10` / Play button).
