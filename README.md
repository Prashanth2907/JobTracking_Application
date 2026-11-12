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

## 💻 Mandatory Technology Stack

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
- **Build Tool:** Apache Maven 3.9+

### Database
- **Engine:** MySQL 8+ (with zero-dependency embedded H2 fallback for instant development)
- **Design:** Normalized 3NF schema, primary keys, foreign key constraints, indexes, timestamps, and enums.

---

## 📁 Repository Structure

```
D:\JobTrack/
├── backend/                             # Spring Boot 3 Application
│   ├── pom.xml                          # Maven build configuration
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
│   │   │       ├── application.properties
│   │   │       ├── application-dev.properties   # H2 instant dev profile
│   │   │       └── application-mysql.properties # Production MySQL profile
│   │   └── test/java/com/jobtrack/      # JUnit 5 & Mockito test suites
├── frontend/                            # React.js Vite Application
│   ├── package.json
│   ├── vite.config.js                   # Reverse proxy configured for /api -> 8080
│   ├── tailwind.config.js
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

### Prerequisites
- **Java:** JDK 17 or higher
- **Node.js:** v18 or higher (v20+ recommended)
- **Maven:** 3.8+ (or use wrapper)
- **MySQL:** 8.0+ (optional: Spring Boot boots with an embedded persistent H2 store in `dev` mode if MySQL is not running)

---

### Step 1: Database Setup (MySQL 8)

1. Open your MySQL client (MySQL Workbench, DBeaver, or CLI):
   ```sql
   CREATE DATABASE jobtrack_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
2. Execute the schema and seed scripts:
   - Run [schema.sql](file:///D:/JobTrack/database/schema.sql)
   - Run [seed.sql](file:///D:/JobTrack/database/seed.sql)

*(Note: If you run Spring Boot with `-Dspring.profiles.active=dev`, it automatically creates an embedded database with tables and seeds initial users automatically without needing MySQL).*

---

### Step 2: Start the Backend (Port 8080)

1. Navigate to the backend directory:
   ```bash
   cd D:\JobTrack\backend
   ```
2. Run with the dev profile (H2):
   ```bash
   mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
   ```
   Or run with MySQL:
   ```bash
   mvn spring-boot:run "-Dspring-boot.run.profiles=mysql"
   ```
3. Backend health and documentation will be accessible at:
   - **OpenAPI Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
   - **H2 Console (dev profile):** [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (JDBC URL: `jdbc:h2:file:./data/jobtrack_dev`)

---

### Step 3: Start the Frontend (Port 5173)

1. Navigate to the frontend directory:
   ```bash
   cd D:\JobTrack\frontend
   ```
2. Install dependencies (if not already installed):
   ```bash
   npm install
   ```
3. Launch the development server:
   ```bash
   npm run dev
   ```
4. Open your browser at:
   - **Web App:** [http://localhost:5173](http://localhost:5173)

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

### Backend Unit & Integration Tests
Run backend tests with Maven:
```bash
cd D:\JobTrack\backend
mvn test "-Dspring.profiles.active=dev"
```
**Test Coverage Includes:**
- `ApplicationServiceTest`: Application submission, duplicate prevention, and status transition validation.
- `AuthServiceTest`: User registration, credential hashing, and JWT token issuance.
- `JobServiceTest`: Job creation, vacancy filtering, and recruiter authorization checks.
- `JobTrackApplicationTests`: Full Spring context boot and JPA repository verification.

### Frontend Production Build Test
Verify frontend production bundling:
```bash
cd D:\JobTrack\frontend
npm run build
```

---

## 🛡️ Security Architecture
- **Stateless Authentication:** Spring Security configured with `SessionCreationPolicy.STATELESS`.
- **Password Hashing:** Passwords encrypted using `BCryptPasswordEncoder` with strength 10.
- **RBAC Authorization:** Endpoints guarded via `@PreAuthorize("hasRole('CANDIDATE')")`, `hasRole('RECRUITER')`, or `hasRole('ADMIN')`.
- **Safe File Storage:** Resume files saved with randomized UUID filenames outside the web root; files served through authenticated streaming controller endpoints with sanitized content headers.
- **CORS Configuration:** Configured to allow requests from `http://localhost:5173` and `http://localhost:3000` with credential support.

---

## 📄 License
This project is open-source under the MIT License. Developed for software engineering portfolios, enterprise demonstrations, and Full Stack Java Developer interviews.
