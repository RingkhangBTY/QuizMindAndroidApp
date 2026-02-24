# 🧠 QuizMind

An AI-powered quiz app for students built with **Android (Java)** and **Spring Boot**, where users can generate custom tests on any topic, review answers with explanations, and track their performance history.


## ✨ Features

- 🔐 JWT-based Login & Register
- 🤖 AI-generated questions based on subject, topic & difficulty (Using Gemini free API)
- 📊 Dashboard with stats — total quizzes, avg score, difficulty breakdown
- 📝 Custom test form — subject, description, number of questions, hardness level
- ✅ Test screen with progress bar, answered/left counter, prev/next navigation
- 🔍 Review screen — correct answers highlighted green, wrong answers red, full explanation
- 📜 Test history — all past attempts with score, level, feedback, review & retest options
- 🔄 Retake any previous test directly from history

---

## 🏗️ Tech Stack
### Android
| What | Tech |
|------|------|
| Language | Java |
| HTTP Client | Retrofit 2 |
| Auth | JWT (stored in SharedPreferences) |
| UI | ConstraintLayout, CardView, ViewPager2, RecyclerView |
| Navigation | Activity-based |

### Backend
| What | Tech |
|------|------|
| Framework | Spring Boot |
| Security | Spring Security + JWT Filter |
| Database | JPA / Hibernate |
| API Docs | Swagger / OpenAPI |
| Password | BCrypt (strength 12) |

---

## 🔌 API Endpoints

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `POST` | `/register` | ❌ | Register new user |
| `POST` | `/login/auth` | ❌ | Login → returns JWT token |
| `GET` | `/users` | ✅ | Get current user details |
| `GET` | `/initial_data` | ✅ | Get dashboard payload |

    - Will add more

---

## 🔐 Auth Flow

```
App Launch
    │
    ├── Token in SharedPreferences? ──► YES → MainActivity
    │
    └── NO → LoginActivity
                │
                ├── Login  → POST /login/auth?username=x&pass=y
                │            ← returns raw JWT string
                │            → saved to SharedPreferences
                │
                └── Register → POST /register (body: username, pass, email)
                               → switches to login mode on success
```

Every request after login automatically attaches:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```
via OkHttp interceptor in `RetrofitClient.java`.

---

## 📁 Project Structure

```
app/src/main/java/com/ringkhang/myapplication/
│
├── activities/
│   ├── LoginActivity.java
│   ├── MainActivity.java
│   ├── TestFormActivity.java
│   ├── TestActivity.java
│   ├── TestReviewActivity.java
│   └── HistoryActivity.java
│
├── adapters/
│   ├── QuestionsVpAdapter.java
│   ├── TestReviewVPAdapter.java
│   └── HistoryAdapter.java
│
├── models/
│   ├── Question.java
│   ├── UserDetailsTable.java
│   ├── UserDetailsDTO.java
│   └── InitialAppPayloadDTO.java
│
├── network/
│   ├── RetrofitClient.java
│   └── AuthApiService.java
│
├── utils/
│   └── SessionManager.java
│
└── views/
    └── BottomNavView.java
```
 - Will add more.
---

## ⚙️ Setup & Run

### Backend (Spring Boot)
```bash
# Clone and run
./mvnw spring-boot:run

# Runs on
http://localhost:8080
```

### Android
1. Open project in **Android Studio**
2. Update base URL in `RetrofitClient.java`:
```java
// Emulator
private static final String BASE_URL = "http://10.0.2.2:8080/";

// Real device (same WiFi — use your PC's IPv4)
private static final String BASE_URL = "http://192.168.x.x:8080/";
```
3. Run on device or emulator

---

## 📦 Dependencies

```gradle
// Retrofit
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
implementation 'com.squareup.retrofit2:converter-scalars:2.9.0'

// OkHttp logging
implementation 'com.squareup.okhttp3:logging-interceptor:4.11.0'
```

---

## 🚧 Roadmap

- [ ] Connect test generation to AI backend
- [ ] Save test results to server
- [ ] Load real test history from API
- [ ] Push notifications for daily quiz reminders
- [ ] Dark mode

---

## 👤 Author

**Ringkhang**  
Built with ☕ Java + Spring Boot + PostgreSQL

---

## 📱 App Screenshots

| Home | History |
|------|---------|
| ![Home](screen_home.jpeg) | ![History](screen_history.jpeg) |

| Test Form | Test Screen |
|-----------|------------|
| ![Test Form](screen_test_form.jpeg) | ![Test](screen_test.jpeg) |

| Review |
|--------|
| ![Review](screen_review.jpeg) |

---
