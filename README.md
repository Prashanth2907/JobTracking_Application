# JobTrack – Full Stack Job Application & Recruitment Management System

JobTrack is an enterprise-grade, full-stack recruitment and job application management platform built with a **Java Spring Boot 3 REST backend**, a **modern React.js frontend**, and a **normalized MySQL 8 database**. It connects job seekers with hiring recruiters and provides a centralized governance console for platform administrators.

---

## 🌟 Highlights & Key Features

### Three Distinct Role-Based Portals

| Role | Portals & Primary Capabilities |
| :--- | :--- |
| **Candidate** | • Browse & filter jobs by title, location, type, experience, and salary<br>• 1-Click job applications with stored or uploaded resume & cover letter<br>• Application status tracking with real-time pipeline visualizer<br>• Profile & resume manager with technical skills tagging<br>• Interview schedules viewer with direct Google Meet/video call links |
| **Recruiter** | • Post, edit, publish, and close job postings<br>• Candidate pipeline dashboard with applicant tracking across 6 stages<br>• Candidate profile review and secure resume download<br>• Real-time application stage transitions (Applied → Review → Shortlist → Interview → Selected/Rejected)<br>• Schedule candidate interviews with meeting links, agendas, and notifications<br>• Employer company profile and branding management |
| **Admin** | • Super Admin dashboard with system metrics & data distribution charts<br>• Complete user management with account activation & deactivation controls<br>• Platform-wide job posting moderation & removal<br>• Global audit logs of all applications across all employers |

---

## 🏗️ System Architecture

```
                      +-----------------------------+
                      |     React.js Frontend       |
                      |   (Vite + Tailwind CSS)     |
                      |    Port 5173 / Port 3000    |
                      +--------------+--------------+
                                     |
                                     | HTTP / JSON (Axios)
                                     | Bearer JWT Token
                                     v
                      +-----------------------------+
                      |   Spring Boot 3.3 Backend   |
                      |          Port 8080          |
                      +--------------+--------------+
                                     |
         +---------------------------+---------------------------+
         |                           |                           |
         v                           v                           v
+------------------+       +------------------+       +-------------------+
| Spring Security  |       | Spring Data JPA  |       | Multipart Storage |
| JWT Verification |       | Hibernate ORM    |       | Local Resumes Dir |
+------------------+       +---------+--------+       +-------------------+
                                     |
                                     | JDBC / SQL
                                     v
                      +-----------------------------+
                      |       MySQL 8 Database      |
                      |  (or Embedded H2 Dev Mode)  |
                      |          Port 3306          |
                      +-----------------------------+
```

---

## 💻 Technology Stack

### Frontend
- **Framework:** React.js (JavaScript ES6+, pure `.js` and `.jsx` files only)
- **Styling:** Tailwind CSS with responsive layout and modern utility classes
- **Routing:** React Router DOM v6 with role-based `ProtectedRoute` guards
- **HTTP Client:** Axios with JWT request interceptor and 401 response handling
- **Data Visualizations:** Recharts (BarCharts, PieCharts for recruitment pipeline analytics)
- **Icons:** Lucide-React
- **Build Tool:** Vite v5 (fast HMR and optimized production bundle)

### Backend
- **Language:** Java 17+ (fully verified on Java 17, Java 21, and Java 27)
- **Framework:** Spring Boot 3.3.4
- **Web Layer:** Spring Web REST Controllers (`@RestController`)
- **Security:** Spring Security with Stateless Session, BCrypt password hashing, and JJWT 0.12.5
- **Persistence:** Spring Data JPA with Hibernate ORM 6.5
- **Validation:** Jakarta Bean Validation (`@Valid`, `@NotBlank`, `@Email`, etc.)
- **Documentation:** SpringDoc OpenAPI 3 / Swagger UI
- **Database Drivers:** MySQL Connector/J 8 & H2 Database Engine for dev/testing
- **Build Tool:** Apache Maven 3.9+ (includes `mvnw` wrapper)

### Database
- **Engine:** MySQL 8+ (with zero-dependency embedded H2 fallback for instant development)
- **Design:** Normalized 3NF schema, primary keys, foreign key constraints, indexes, timestamps, and enums.

---

## 📁 Repository Structure

```
D:\JobTrack/
├── backend/                             # Spring Boot 3 Application
│   ├── pom.xml                          # Maven build configuration
│   ├── mvnw.cmd / mvnw.ps1              # Maven wrappers
│   ├── Dockerfile                       # Production container build
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/jobtrack/
│   │   │   │   ├── config/              # SecurityConfig, DataInitializer, StorageConfig
│   │   │   │   ├── controller/          # Auth, User, Job, App, Resume, Interview, Admin
│   │   │   │   ├── dto/                 # Request & Response POJOs with clean builders
│   │   │   │   ├── entity/              # User, Job, Application, Resume, Interview, etc.
│   │   │   │   ├── enums/               # Role, JobStatus, ApplicationStatus, InterviewType
│   │   │   │   ├── exception/           # RestControllerAdvice & custom exceptions
│   │   │   │   ├── mapper/              # EntityMapper for DTO translations
│   │   │   │   ├── repository/          # 9 Spring Data JPA repositories
│   │   │   │   ├── security/            # JwtUtil, JwtAuthenticationFilter, UserPrincipal
│   │   │   │   └── service/             # Business logic interfaces & implementations
│   │   │   └── resources/
│   │   │       ├── application.properties       # Default: dev profile
│   │   │       ├── application-dev.properties   # H2 instant dev profile
│   │   │       └── application-mysql.properties # Production MySQL profile
│   │   └── test/java/com/jobtrack/      # JUnit 5 & Mockito test suites
├── frontend/                            # React.js Vite Application
│   ├── package.json
│   ├── vite.config.js                   # Reverse proxy configured for /api -> 8080
│   ├── tailwind.config.js
│   ├── Dockerfile                       # Production container build
│   ├── nginx.conf                       # Production Nginx reverse proxy
│   ├── index.html
│   └── src/
│       ├── components/                  # Navbar, Sidebar, JobCard, JobFilters, Modals
│       ├── context/                     # AuthContext with token & user persistence
│       ├── pages/
│       │   ├── LandingPage.jsx          # Public showcase & featured jobs
│       │   ├── LoginPage.jsx            # Sign in with 1-Click demo account filling
│       │   ├── RegisterPage.jsx         # Candidate & Recruiter registration tabs
│       │   ├── JobSearchPage.jsx        # Live keyword, location, salary & type filters
│       │   ├── JobDetailsPage.jsx       # Job specs and application modal trigger
│       │   ├── candidate/               # Candidate Dashboard, Apps, Profile, Interviews
│       │   ├── recruiter/               # Recruiter Dashboard, Post Job, Applicants, Interviews
│       │   └── admin/                   # Admin Console, User Moderation, Job Auditing
│       ├── routes/                      # AppRoutes and ProtectedRoute RBAC guards
│       ├── services/                    # Axios API integration modules
│       └── utils/                       # Date, salary, badge & role formatters
├── database/
│   ├── schema.sql                       # Complete MySQL 8 DDL with foreign keys & indexes
│   └── seed.sql                         # Rich seed data with BCrypt-hashed credentials
├── .vscode/                             # VS Code tasks for 1-click execution & debugging
├── docker-compose.yml                   # 1-Command full-stack container orchestration
├── run-backend.bat                      # 1-Click Windows backend launcher
├── run-frontend.bat                     # 1-Click Windows frontend launcher
└── README.md
```

---

## 🔑 Default Seed Test Accounts

All pre-seeded test accounts use the password: **`Password@123`**

| Role | Email | Password | Features Accessible |
| :--- | :--- | :--- | :--- |
| **Administrator** | `admin@jobtrack.com` | `Password@123` | Full admin console, manage users, moderate jobs, audit all applications |
| **Recruiter** | `recruiter@jobtrack.com`<br>`sarah.recruiter@techcorp.com` | `Password@123` | Post vacancies, manage applicants, schedule interviews, update company profile |
| **Candidate** | `candidate@jobtrack.com`<br>`john.dev@example.com` | `Password@123` | Browse jobs, submit applications with resume, manage skills, view interview schedule |

> **Tip:** The Login page includes **⚡ 1-Click Demo Buttons** that instantly fill in credentials for Candidate, Recruiter, and Admin!

---

## 🚀 Quick Start Guide

### Option A: 1-Click Startup (Windows)
1. Double-click [**`run-backend.bat`**](file:///D:/JobTrack/run-backend.bat) (Starts Spring Boot on port `8080`).
2. Double-click [**`run-frontend.bat`**](file:///D:/JobTrack/run-frontend.bat) (Starts React Vite on port `5173`).
3. Open your browser at **[http://localhost:5173](http://localhost:5173)**.

---

### Option B: Terminal Startup

#### 1. Start the Backend (Port 8080)
```powershell
cd D:\JobTrack\backend
mvn spring-boot:run
```
*(Or use `.\mvnw spring-boot:run`)*

- Swagger API Docs: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (JDBC URL: `jdbc:h2:file:./data/jobtrack_dev`)

#### 2. Start the Frontend (Port 5173)
```powershell
cd D:\JobTrack\frontend
npm run dev
```
- Web Application: [http://localhost:5173](http://localhost:5173)

---

## 🗄️ Database Setup (Optional: Using MySQL 8)

By default, the backend boots with an embedded persistent database in `dev` mode with zero configuration.

To switch to **MySQL 8**:
1. Open your MySQL client and create the database:
   ```sql
   CREATE DATABASE jobtrack_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
2. Run the initialization scripts:
   - Execute [**`database/schema.sql`**](file:///D:/JobTrack/database/schema.sql)
   - Execute [**`database/seed.sql`**](file:///D:/JobTrack/database/seed.sql)
3. Update credentials in [`backend/src/main/resources/application-mysql.properties`](file:///D:/JobTrack/backend/src/main/resources/application-mysql.properties).
4. Run:
   ```powershell
   cd D:\JobTrack\backend
   mvn spring-boot:run "-Dspring-boot.run.profiles=mysql"
   ```

---

## 📡 REST API Reference

### Authentication (`/api/auth`)
- `POST /api/auth/login` - Authenticate user, returns JWT and user profile
- `POST /api/auth/register/candidate` - Register candidate account
- `POST /api/auth/register/recruiter` - Register recruiter with company account
- `GET /api/auth/me` - Retrieve authenticated user profile

### Jobs Management (`/api/jobs`)
- `GET /api/jobs` - Search and filter jobs (pagination, location, skills, salary)
- `GET /api/jobs/{id}` - Retrieve job details by ID
- `POST /api/jobs` - Create a new job vacancy *(Recruiter only)*
- `PUT /api/jobs/{id}` - Update job details *(Owner Recruiter or Admin)*
- `DELETE /api/jobs/{id}` - Delete job listing *(Owner Recruiter or Admin)*
- `GET /api/jobs/my-jobs` - Get all jobs posted by current recruiter
- `PATCH /api/jobs/{id}/publish` - Publish job vacancy
- `PATCH /api/jobs/{id}/close` - Close job vacancy

### Job Applications (`/api/applications`)
- `POST /api/applications` - Apply for a position *(Candidate only)*
- `GET /api/applications/my` - Get current candidate applications
- `GET /api/applications/job/{jobId}` - Get all applicants for a job *(Recruiter only)*
- `GET /api/applications/{id}` - Get application details
- `PATCH /api/applications/{id}/status` - Advance pipeline stage *(Recruiter only)*

### Resumes (`/api/resumes`)
- `POST /api/resumes/upload` - Upload PDF/DOCX resume (multipart, max 5MB)
- `GET /api/resumes/my` - Get current candidate active resume metadata
- `GET /api/resumes/{id}/download` - Stream and download resume file safely

### Interviews (`/api/interviews`)
- `POST /api/interviews` - Schedule candidate interview *(Recruiter only)*
- `PUT /api/interviews/{id}` - Modify interview details
- `PATCH /api/interviews/{id}/status` - Update interview status (COMPLETED, CANCELLED)
- `GET /api/interviews/my` - Get scheduled interviews for current user

### Administrator (`/api/admin`)
- `GET /api/admin/stats` - Total candidates, recruiters, companies, jobs, applications
- `GET /api/admin/users` - Paginated user directory with role & status filters
- `PATCH /api/admin/users/{id}/toggle-status` - Activate or deactivate account
- `GET /api/admin/jobs` - Platform-wide job listing moderation
- `DELETE /api/admin/jobs/{id}` - Remove inappropriate job posting
- `GET /api/admin/applications` - Global audit of all candidate applications

---

## 🧪 Testing Suite

### Backend Tests (JUnit 5 & Mockito)
```powershell
cd D:\JobTrack\backend
mvn test
```
- `ApplicationServiceTest`: Validates submission flow and stage advances.
- `AuthServiceTest`: Validates BCrypt hashing, JWT generation, and credentials.
- `JobServiceTest`: Validates CRUD and recruiter ownership security.
- `JobTrackApplicationTests`: Validates full Spring context and JPA mappings.

### Frontend Production Build Test
```powershell
cd D:\JobTrack\frontend
npm run build
```

---

## 🐳 Docker Deployment

To launch the complete stack (MySQL + Backend + Frontend/Nginx) with a single command:
```powershell
cd D:\JobTrack
docker compose up -d --build
```
Access the application at `http://localhost`.

---

## 📄 License
This project is open-source under the MIT License. Built for full-stack portfolio showcases, production architectures, and Full Stack Java Developer interviews.
