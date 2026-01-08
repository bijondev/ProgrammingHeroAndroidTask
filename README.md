# Programming Hero Android Task

A modern Android application for the Programming Hero interview task, demonstrating Offline-First architecture, MVVM, and Jetpack Compose.

## 🚀 Setup & Requirements

### 1. Beeceptor Mock API
The app requires a remote API to fetch course data.
1. Go to [Beeceptor](https://beeceptor.com/) and create a new endpoint (e.g., `my-ph-task`).
2. Create a mock rule for `GET /courses` to return the JSON structure provided in the task description.
3. Open `app/src/main/java/com/example/ph_android_task/data/remote/CourseService.kt`.
4. Update the `ENDPOINT` constant with your Beeceptor URL:
   ```kotlin
   const val ENDPOINT = "https://mp7eebd038e5f22e63df.free.beeceptor.com/courses"
   ```

### 2. Build & Run
- Open the project in Android Studio (Koala or later recommended).
- Sync Gradle.
- Run on an Emulator or Device (Min SDK 24).

## 🏗 Architecture & Tech Stack

This project uses a **Clean Architecture** approach with **MVVM**.

- **Language**: Kotlin
- **UI**: Jetpack Compose (Material3) + Navigation Compose
- **DI**: Hilt (Dagger)
- **Network**: Ktor Client (ContentNegotiation, Logging)
- **Database**: Room (Offline First, Flow, Full-Text Search)
- **Async**: Coroutines & Flow

### Key Features Implemented
- **Offline First**: Courses are cached in Room. The app displays local data immediately and syncs with the network in the background.
- **Search**: Real-time filtering logic on the Database layer (SQLite `LIKE` query) using Flow.
- **Enrollment**: "Mark as Enrolled" updates the local database state (`isEnrolled` flag) while preserving it during network syncs.
- **DI**: Full dependency graph via `AppModule`.

## 📂 Project Structure

- `data`:
    - `local`: Room Database, DAO, Entity.
    - `remote`: Ktor Service, DTOs.
    - `repository`: Implementation of Repository, combining Local and Remote.
- `domain`:
    - `model`: UI-agnostic data class.
    - `repository`: Interface for data access.
- `di`: Hilt Modules.
- `ui`:
    - `screens`: Feature screens (CourseList, CourseDetail) with ViewModels.
    - `navigation`: Navigation graph.

## ✅ Task Checklist
- [x] Data Fetching (Ktor)
- [x] Offline Persistence (Room)
- [x] Concurrency (Flow/Coroutines)
- [x] Architecture (MVVM)
- [x] Dependency Injection (Hilt)
- [x] UI (Compose)
- [x] Search/Filter (DB Level)
- [x] Navigation (Compose)
- [x] Interactive Enroll Status

## 📝 Mock Data JSON (Reference)
Use this structure in Beeceptor:
```json
[
  {
    "course_id": "KOTLIN-001",
    "title": "Android App Development with Compose",
    "description_short": "Build modern Android apps from scratch...",
    "instructor": {
      "name": "Prof. Anika",
      "expertise_level": "Senior Developer"
    },
    "duration_weeks": 8,
    "price_usd": 49.99,
    "is_premium": true,
    "tags": ["Compose", "MVVM", "Coroutines"],
    "rating": 4.8
  },
  {
    "course_id": "ANDROID-002",
    "title": "Advanced Clean Architecture",
    "description_short": "Master SOLID principles and modularization...",
    "instructor": {
      "name": "Dev John",
      "expertise_level": "Architect"
    },
    "duration_weeks": 6,
    "price_usd": 59.99,
    "is_premium": false,
    "tags": ["Architecture", "Testing", "Hilt"],
    "rating": 4.9
  }
]
```
