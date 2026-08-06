# 📚 LibraQuiz Microservices Platform

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.0-blue.svg)](https://spring.io/projects/spring-cloud)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![React](https://img.shields.io/badge/React-18-61DAFB.svg)](https://reactjs.org/)
[![Vite](https://img.shields.io/badge/Vite-5.1-646CFF.svg)](https://vitejs.dev/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1.svg)](https://www.mysql.com/)

**LibraQuiz** is an enterprise-grade, distributed microservices platform combining a **Learning Management System (LMS)**, a **Digital Library Management System**, an **Online Examination & Quiz Engine**, and an **AI Recommendation Assistant**.

---

## 🏗️ Architecture Overview

LibraQuiz follows a **Database-per-Service** microservice pattern powered by **Spring Cloud Eureka** for service discovery and **Spring Cloud API Gateway** for unified routing and security filtering.

```
                              +---------------------------------------+
                              |         React + Vite Frontend         |
                              |              (Port 5173)              |
                              +-------------------+-------------------+
                                                  |
                                                  v
                              +---------------------------------------+
                              |         Spring Cloud Gateway          |
                              |              (Port 8080)              |
                              +-------------------+-------------------+
                                                  |
                     +----------------------------+----------------------------+
                     |                            |                            |
                     v                            v                            v
          +--------------------+        +--------------------+        +--------------------+
          | Eureka Discovery   |        |  Identity Service  |        |    User Service    |
          |   (Port 8761)      |        |    (Port 8081)     |        |    (Port 8082)     |
          +--------------------+        +--------------------+        +--------------------+
                     |                            |                            |
                     +----------------------------+----------------------------+
                                                  |
        +------------------+------------------+---+------------------+------------------+
        |                  |                  |                      |                  |
        v                  v                  v                      v                  v
 +-------------+    +-------------+    +-------------+        +-------------+    +-------------+
 | Category    |    | Book        |    | Library Txn |        | Course      |    | Question    |
 | Service     |    | Service     |    | Service     |        | Service     |    | Bank        |
 | (Port 8083) |    | (Port 8084) |    | (Port 8085) |        | (Port 8086) |    | (Port 8087) |
 +-------------+    +-------------+    +-------------+        +-------------+    +-------------+
        |                  |                  |                      |                  |
        +------------------+------------------+---+------------------+------------------+
                                                  |
        +------------------+------------------+---+------------------+------------------+
        |                  |                  |                      |                  |
        v                  v                  v                      v                  v
 +-------------+    +-------------+    +-------------+        +-------------+    +-------------+
 | Question    |    | Quiz        |    | Result      |        | AI          |    | Examination |
 | Management  |    | Service     |    | Service     |        | Service     |    | Service     |
 | (Port 8088) |    | (Port 8089) |    | (Port 8091) |        | (Port 8092) |    | (Port 8094) |
 +-------------+    +-------------+    +-------------+        +-------------+    +-------------+
                                                  |
                                                  v
                                       +--------------------+
                                       |  Notification Svc  |
                                       |    (Port 8093)     |
                                       +--------------------+
```

---

## 🌟 Key Features & Role Portals

### 🎓 1. Student Portal
* **Course Hub**: Browse enrolled courses, watch video lessons, read course materials.
* **Online Examination**: Take timed quizzes & exams with real-time countdown, automatic save, and attempt tracking.
* **Result Analytics**: Instant test evaluation, grade breakdown, performance history.
* **Digital Library**: Search book catalog, request book issues, view active loans and fine statuses.

### 👨‍🏫 2. Teacher / Instructor Portal
* **Course Creator**: Manage course curriculum, upload modules, update lesson metadata.
* **Question Bank Manager**: Author multi-choice/subjective questions categorized by difficulty and tags.
* **Quiz Builder**: Build custom exams, set passing scores, time limits, and question weighting.
* **Grade Center**: Review student exam responses, view statistics and score reports.

### 📚 3. Librarian Portal
* **Catalog Management**: Add/update books with ISBN, author, category, publisher, and copy inventory.
* **Transaction Engine**: Process book issue requests, handle returns, calculate overdue fines automatically.
* **Overdue Monitor**: Track pending returns and issue notification alerts.

### 🛡️ 4. Admin Portal
* **User Management**: Role-based access control (Admin, Student, Teacher, Librarian).
* **Category Registry**: Manage global taxonomy for library books and LMS courses.
* **System Monitor**: Central overview of platform metrics, active exams, and transactions.

---

## ⚙️ Microservices Inventory & Database Mapping

| Service Name | Port | Database Name | API Gateway Route | Responsibilities |
|---|---|---|---|---|
| **EurekaServer** | `8761` | *N/A* | *N/A* | Netflix Eureka Service Discovery Engine |
| **ApiGateway** | `8080` | *N/A* | `/` | Central Entry Point, CORS, JWT Authorization |
| **IdentityService** | `8081` | `lq_identitydb` | `/api/auth/**` | User authentication, token issuance & validation |
| **UserService** | `8082` | `lq_userdb` | `/api/users/**` | User profile data, contact details, role scopes |
| **CategoryService** | `8083` | `lq_categorydb` | `/api/categories/**` | Subject categories for books & LMS courses |
| **BookService** | `8084` | `lq_bookdb` | `/api/books/**` | Library inventory, ISBN search, book stock |
| **LibraryTransactionService** | `8085` | `lq_librarytxndb` | `/api/transactions/**` | Book issuing, return tracking, overdue fine calculation |
| **CourseService** | `8086` | `lq_coursedb` | `/api/courses/**` | LMS courses, lessons, modules, enrollments |
| **QuestionBankService** | `8087` | `lq_questionbankdb` | `/api/questions/**` | Storage & management of question banks |
| **QuestionManagementService** | `8088` | `lq_questionmgmtdb` | `/api/question-management/**` | Question authoring, tag mapping, options |
| **QuizService** | `8089` | `lq_quizdb` | `/api/quizzes/**` | Quiz configuration, schedule, passing rules |
| **ResultService** | `8091` | `lq_resultdb` | `/api/results/**` | Exam scoring, grade analytics, performance logs |
| **AIService** | `8092` | `lq_aidb` | `/api/ai/**` | AI-assisted quiz question generation & recommendations |
| **NotificationService** | `8093` | `lq_notificationdb` | `/api/notifications/**` | System alerts, email notifications, return reminders |
| **ExaminationService** | `8094` | `lq_examinationdb` | `/api/exams/**` | Real-time examination session runner & submission |

---

## 🛠️ Technology Stack

### Backend
* **Language & Framework**: Java 17, Spring Boot 3.2.3
* **Cloud Architecture**: Spring Cloud 2023.0.0 (Eureka Server, Spring Cloud Gateway)
* **Database & Persistence**: Spring Data JPA, Hibernate, MySQL 8.0
* **Security**: JWT (`jjwt 0.11.5`), Spring Security
* **Build Tool**: Apache Maven

### Frontend
* **Library & Build**: React 18, Vite 5
* **State Management**: Redux Toolkit (`@reduxjs/toolkit`), React Redux
* **Routing**: React Router DOM v6
* **UI & Styling**: Modern CSS, Lucide React Icons (`lucide-react`)
* **HTTP Client**: Axios

---

## 🚀 Getting Started

### Prerequisites
Make sure you have the following installed on your machine:
* **Java Development Kit (JDK 17+)**
* **Apache Maven 3.8+**
* **Node.js 18+ & npm**
* **MySQL Server 8.0+** running on `localhost:3306` (Default credentials configured: `root` / `vinit@1999`)

---

### Step 1: Database Setup
The microservices are configured to automatically create their required MySQL databases (`createDatabaseIfNotExist=true`). Ensure your MySQL service is running locally on port `3306`.

If you wish to change database credentials, update `src/main/resources/application-dev.yml` in each service directory.

---

### Step 2: Build & Start Backend Microservices

1. **Build all services using Maven**:
   ```bash
   mvn clean install -DskipTests
   ```

2. **Start the Eureka Server first**:
   ```bash
   cd EurekaServer
   mvn spring-boot:run
   ```
   *Verify Eureka Dashboard is live at: `http://localhost:8761`*

3. **Start the API Gateway**:
   ```bash
   cd ApiGateway
   mvn spring-boot:run
   ```
   *Gateway will run at `http://localhost:8080`*

4. **Start Core Microservices** (in separate terminal windows or run via your preferred IDE):
   * `IdentityService` (Port 8081)
   * `UserService` (Port 8082)
   * `CategoryService` (Port 8083)
   * `BookService` (Port 8084)
   * `LibraryTransactionService` (Port 8085)
   * `CourseService` (Port 8086)
   * `QuestionBankService` (Port 8087)
   * `QuestionManagementService` (Port 8088)
   * `QuizService` (Port 8089)
   * `ResultService` (Port 8091)
   * `AIService` (Port 8092)
   * `NotificationService` (Port 8093)
   * `ExaminationService` (Port 8094)

---

### Step 3: Run the Frontend SPA

1. Navigate to the `frontend` folder:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

4. Open your browser and navigate to:
   ```
   http://localhost:5173
   ```

---

## 📂 Project Structure

```
LibraQuiz1/
├── pom.xml                             # Root Parent Maven POM
├── EurekaServer/                       # Service Registry (Port 8761)
├── ApiGateway/                         # API Gateway Routing & Auth (Port 8080)
├── IdentityService/                    # Auth & JWT Service (Port 8081)
├── UserService/                        # User Profile Service (Port 8082)
├── CategoryService/                    # Category Taxonomy Service (Port 8083)
├── BookService/                        # Book Catalog Service (Port 8084)
├── LibraryTransactionService/          # Issue / Return / Fine Service (Port 8085)
├── CourseService/                      # LMS Course Service (Port 8086)
├── QuestionBankService/                # Question Repository Service (Port 8087)
├── QuestionManagementService/          # Question Authoring Service (Port 8088)
├── QuizService/                        # Quiz Rule Engine Service (Port 8089)
├── ResultService/                      # Score Analytics Service (Port 8091)
├── AIService/                          # AI Assistance Service (Port 8092)
├── NotificationService/                # System Alerts Service (Port 8093)
├── ExaminationService/                 # Live Exam Engine (Port 8094)
└── frontend/                           # React + Vite Frontend SPA
    ├── src/
    │   ├── api/                        # Axios API Client
    │   ├── app/                        # Redux Store Configuration
    │   ├── components/                 # Reusable UI Components
    │   ├── features/                   # Redux Slices (Auth, Books, Quizzes, etc.)
    │   ├── layouts/                    # Navigation & Role Layouts
    │   └── pages/                      # Role Portals (admin, student, teacher, librarian)
    ├── package.json
    └── vite.config.js
```

---

## 📜 License

This project is open-source and available under the **MIT License**.
