# 📓 Journal App — Secure REST API

A production-ready multi-user journaling REST API built with **Spring Boot**, **Spring Security**, and **MongoDB Atlas**. Features complete authentication, role-based authorization, cloud database integration, and atomic transaction management.

---

## 🚀 Features

- ✅ **User Authentication** — Spring Security with BCrypt password encoding
- ✅ **Role-Based Authorization** — USER and ADMIN roles with protected endpoints
- ✅ **Multi-User Journals** — Each user can only access their own journal entries
- ✅ **MongoDB Atlas** — Cloud-hosted NoSQL database with @DBRef relationships
- ✅ **Transactional Operations** — @Transactional ensures atomic writes across collections
- ✅ **Global Exception Handling** — Centralized @RestControllerAdvice for consistent error responses
- ✅ **Ownership Verification** — SecurityContextHolder used to verify resource ownership on every request

---

## 🛠 Tech Stack

| Category | Technology |
|---|---|
| Backend Framework | Spring Boot 3.5 |
| Security | Spring Security 6 |
| Database | MongoDB Atlas |
| Build Tool | Maven |
| Language | Java 17 |
| API Testing | Postman |

---

## 📁 Project Structure

```
src/main/java/com/example/journelApp/
├── config/
│   └── SpringSecurity.java       # Security config, roles, route protection
├── controller/
│   ├── JournalEntryController.java  # Journal CRUD endpoints
│   ├── UserController.java          # User management endpoints
│   ├── AdminController.java         # Admin-only endpoints
│   └── PublicController.java        # Public endpoints (no auth required)
├── entity/
│   ├── JournalEntry.java
│   └── User.java
├── repository/
│   ├── JournalEntryRepository.java
│   └── UserRepository.java
├── service/
│   ├── JournalEntryService.java
│   ├── UserService.java
│   └── UserDetailsServiceImpl.java  # Spring Security integration
└── exceptions/
    └── GlobalExceptionHandler.java
```

---

## 🔐 API Endpoints

### Public
| Method | Endpoint | Description |
|---|---|---|
| POST | `/user` | Register new user |

### User (Authenticated)
| Method | Endpoint | Description |
|---|---|---|
| GET | `/journal` | Get all journal entries of logged-in user |
| POST | `/journal` | Create new journal entry |
| GET | `/journal/id/{id}` | Get specific entry (ownership verified) |
| PUT | `/journal/id/{id}` | Update entry (ownership verified) |
| DELETE | `/journal/id/{id}` | Delete entry (ownership verified) |
| GET | `/user` | Get logged-in user details |
| PUT | `/user` | Update user profile |

### Admin Only
| Method | Endpoint | Description |
|---|---|---|
| GET | `/admin/all-users` | Get all users |
| POST | `/admin/create-admin` | Create admin user |

---

## ⚙️ Setup & Run Locally

### Prerequisites
- Java 17+
- Maven
- MongoDB Atlas account (or local MongoDB)

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/Yugbh07/Journal-App.git
cd Journal-App

# 2. Create application.yml in src/main/resources/
```

```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://<username>:<password>@cluster0.xxxxx.mongodb.net/journaldb
      auto-index-creation: true
```

```bash
# 3. Run the application
mvn spring-boot:run
```

---

## 🔑 Authentication

This API uses **HTTP Basic Authentication**.

```
Authorization: Basic <base64(username:password)>
```

Example using Postman:
- Go to **Authorization** tab
- Select **Basic Auth**
- Enter your username and password

---

## 🏗 Key Design Decisions

**@DBRef for relationships** — User and JournalEntry are stored in separate collections, linked by reference. This keeps documents lean and avoids data duplication.

**@Transactional on saveEntry** — When a new journal entry is created, two DB operations happen: saving the entry and updating the user's entry list. @Transactional ensures both succeed or both rollback.

**SecurityContextHolder for ownership** — Instead of passing userName as a path variable (which could be spoofed), the logged-in user's identity is extracted directly from the security context on every request.

**GlobalExceptionHandler** — All exceptions are caught in one place using @RestControllerAdvice, returning consistent JSON error responses instead of stack traces.

---

## 📬 Contact

**Yug Bhandari**
- 📧 yugbh07@gmail.com
- 💼 [LinkedIn](https://linkedin.com/in/yugbhandari)
- 💻 [LeetCode](https://leetcode.com/u/YugBh07)
