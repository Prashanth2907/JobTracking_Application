package com.jobtrack.config;

import com.jobtrack.entity.*;
import com.jobtrack.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final ResumeRepository resumeRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, CompanyRepository companyRepository, RecruiterProfileRepository recruiterProfileRepository, CandidateProfileRepository candidateProfileRepository, ResumeRepository resumeRepository, JobRepository jobRepository, ApplicationRepository applicationRepository, InterviewRepository interviewRepository, NotificationRepository notificationRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.resumeRepository = resumeRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.interviewRepository = interviewRepository;
        this.notificationRepository = notificationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        String encodedPassword = passwordEncoder.encode("Password@123");

        if (userRepository.count() > 0) {
            log.info("JobTrack database contains existing records. Verifying essential demo accounts...");
            ensureDemoAccounts(encodedPassword);
            return;
        }

        log.info("Seeding initial JobTrack platform data...");

        // 1. Create Admin
        User admin = userRepository.save(User.builder()
                .email("admin@jobtrack.com")
                .password(encodedPassword)
                .fullName("System Administrator")
                .phone("+1-555-0100")
                .role(Role.ROLE_ADMIN)
                .status(UserStatus.ACTIVE)
                .build());

        // 2. Create Companies
        Company techCorp = companyRepository.save(Company.builder()
                .name("TechCorp Solutions")
                .description("Global enterprise cloud and software engineering powerhouse delivering modern digital solutions.")
                .website("https://techcorp-example.com")
                .industry("Information Technology")
                .companySize("500-1000 employees")
                .logoUrl("https://images.unsplash.com/photo-1549923746-c502d488b3ea?w=128&h=128&fit=crop")
                .address("100 Innovation Way, Austin, TX")
                .build());

        Company innovateX = companyRepository.save(Company.builder()
                .name("InnovateX Labs")
                .description("Next-generation AI and financial technology startup building scalable distributed systems.")
                .website("https://innovatex-example.io")
                .industry("Fintech / AI")
                .companySize("50-200 employees")
                .logoUrl("https://images.unsplash.com/photo-1572021335469-31706a17aaef?w=128&h=128&fit=crop")
                .address("452 Silicon Blvd, San Francisco, CA")
                .build());

        // 3. Create Recruiters
        User recruiterPrimary = userRepository.save(User.builder()
                .email("recruiter@jobtrack.com")
                .password(encodedPassword)
                .fullName("Sarah Jenkins")
                .phone("+1-555-0101")
                .role(Role.ROLE_RECRUITER)
                .status(UserStatus.ACTIVE)
                .build());

        recruiterProfileRepository.save(RecruiterProfile.builder()
                .user(recruiterPrimary)
                .company(techCorp)
                .designation("Lead Technical Recruiter")
                .department("Talent Acquisition")
                .contactPhone("+1-555-0101")
                .build());

        User recruiter1 = userRepository.save(User.builder()
                .email("sarah.recruiter@techcorp.com")
                .password(encodedPassword)
                .fullName("Sarah Jenkins")
                .phone("+1-555-0101")
                .role(Role.ROLE_RECRUITER)
                .status(UserStatus.ACTIVE)
                .build());

        recruiterProfileRepository.save(RecruiterProfile.builder()
                .user(recruiter1)
                .company(techCorp)
                .designation("Senior Technical Talent Partner")
                .department("Engineering Talent Acquisition")
                .contactPhone("+1-555-0101")
                .build());

        User recruiter2 = userRepository.save(User.builder()
                .email("alex.talent@innovatex.io")
                .password(encodedPassword)
                .fullName("Alex Rivera")
                .phone("+1-555-0102")
                .role(Role.ROLE_RECRUITER)
                .status(UserStatus.ACTIVE)
                .build());

        recruiterProfileRepository.save(RecruiterProfile.builder()
                .user(recruiter2)
                .company(innovateX)
                .designation("Head of People & Recruiting")
                .department("Human Resources")
                .contactPhone("+1-555-0102")
                .build());

        // 4. Create Candidates
        User candidatePrimary = userRepository.save(User.builder()
                .email("candidate@jobtrack.com")
                .password(encodedPassword)
                .fullName("Alex Johnson")
                .phone("+1-555-0200")
                .role(Role.ROLE_CANDIDATE)
                .status(UserStatus.ACTIVE)
                .build());

        Resume resumePrimary = resumeRepository.save(Resume.builder()
                .candidate(candidatePrimary)
                .originalFilename("Alex_Johnson_FullStack_Resume.pdf")
                .storedFilename("resume_sample_alex_johnson.pdf")
                .fileType("application/pdf")
                .fileSize(148000L)
                .storagePath("uploads/resumes/resume_sample_alex_johnson.pdf")
                .build());

        candidateProfileRepository.save(CandidateProfile.builder()
                .user(candidatePrimary)
                .headline("Full Stack Software Engineer (Java, React & Cloud)")
                .bio("Motivated full stack developer with experience building responsive web apps with React and resilient Spring Boot microservices.")
                .location("San Francisco, CA")
                .currentSalary(BigDecimal.valueOf(110000.00))
                .expectedSalary(BigDecimal.valueOf(130000.00))
                .experienceYears(4)
                .skills("Java, Spring Boot, React.js, JavaScript, MySQL, Docker, REST APIs")
                .educationJson("[{\"degree\":\"B.S. in Computer Science\",\"institution\":\"UC Berkeley\",\"year\":\"2021\",\"grade\":\"3.8 GPA\"}]")
                .experienceJson("[{\"title\":\"Software Engineer\",\"company\":\"Innovate Soft\",\"period\":\"2021 - Present\",\"description\":\"Built full stack web features and RESTful backend APIs.\"}]")
                .activeResume(resumePrimary)
                .build());

        User candidate1 = userRepository.save(User.builder()
                .email("john.dev@example.com")
                .password(encodedPassword)
                .fullName("John Doe")
                .phone("+1-555-0201")
                .role(Role.ROLE_CANDIDATE)
                .status(UserStatus.ACTIVE)
                .build());

        Resume resume1 = resumeRepository.save(Resume.builder()
                .candidate(candidate1)
                .originalFilename("John_Doe_Senior_Java_Engineer.pdf")
                .storedFilename("resume_sample_john_doe.pdf")
                .fileType("application/pdf")
                .fileSize(142580L)
                .storagePath("uploads/resumes/resume_sample_john_doe.pdf")
                .build());

        candidateProfileRepository.save(CandidateProfile.builder()
                .user(candidate1)
                .headline("Senior Full Stack Java & React Developer")
                .bio("Passionate software craftsman with 6+ years building microservices with Spring Boot, Spring Security, and modern React SPAs.")
                .location("Austin, TX")
                .currentSalary(BigDecimal.valueOf(115000.00))
                .expectedSalary(BigDecimal.valueOf(135000.00))
                .experienceYears(6)
                .skills("Java, Spring Boot, React.js, MySQL, Docker, Kubernetes, AWS, REST APIs, Microservices")
                .educationJson("[{\"degree\":\"B.S. in Computer Science\",\"institution\":\"University of Texas at Austin\",\"year\":\"2020\",\"grade\":\"3.8 GPA\"}]")
                .experienceJson("[{\"title\":\"Full Stack Developer\",\"company\":\"CloudTech Inc\",\"period\":\"2021 - Present\",\"description\":\"Led migration from monolith to Spring Boot microservices, decreasing latency by 35%.\"}]")
                .activeResume(resume1)
                .build());


        User candidate2 = userRepository.save(User.builder()
                .email("emily.qa@example.com")
                .password(encodedPassword)
                .fullName("Emily Watson")
                .phone("+1-555-0202")
                .role(Role.ROLE_CANDIDATE)
                .status(UserStatus.ACTIVE)
                .build());

        Resume resume2 = resumeRepository.save(Resume.builder()
                .candidate(candidate2)
                .originalFilename("Emily_Watson_FullStack_QA.pdf")
                .storedFilename("resume_sample_emily_watson.pdf")
                .fileType("application/pdf")
                .fileSize(128900L)
                .storagePath("uploads/resumes/resume_sample_emily_watson.pdf")
                .build());

        candidateProfileRepository.save(CandidateProfile.builder()
                .user(candidate2)
                .headline("Senior Software Quality Engineer (SDET)")
                .bio("Automation QA engineer specializing in Selenium, Cypress, REST-Assured, JUnit, and CI/CD pipelines.")
                .location("Chicago, IL")
                .currentSalary(BigDecimal.valueOf(95000.00))
                .expectedSalary(BigDecimal.valueOf(110000.00))
                .experienceYears(4)
                .skills("Java, JUnit 5, Selenium, Cypress, TestNG, REST-Assured, Jenkins, Docker, Postman")
                .educationJson("[{\"degree\":\"B.E. in Software Engineering\",\"institution\":\"University of Illinois\",\"year\":\"2022\",\"grade\":\"3.7 GPA\"}]")
                .experienceJson("[{\"title\":\"SDET Lead\",\"company\":\"Fintech Corp\",\"period\":\"2022 - Present\",\"description\":\"Designed and executed end-to-end automated testing suites.\"}]")
                .activeResume(resume2)
                .build());

        User candidate3 = userRepository.save(User.builder()
                .email("michael.cloud@example.com")
                .password(encodedPassword)
                .fullName("Michael Chang")
                .phone("+1-555-0203")
                .role(Role.ROLE_CANDIDATE)
                .status(UserStatus.ACTIVE)
                .build());

        candidateProfileRepository.save(CandidateProfile.builder()
                .user(candidate3)
                .headline("Cloud Infrastructure & DevOps Engineer")
                .bio("Specialist in AWS, Kubernetes, Terraform, and high-resiliency zero-downtime deployments.")
                .location("Seattle, WA")
                .currentSalary(BigDecimal.valueOf(130000.00))
                .expectedSalary(BigDecimal.valueOf(150000.00))
                .experienceYears(7)
                .skills("AWS, Kubernetes, Docker, Terraform, CI/CD, Java, Spring Cloud, Linux, Prometheus")
                .build());

        // 5. Create Jobs
        Job job1 = jobRepository.save(Job.builder()
                .recruiter(recruiter1)
                .company(techCorp)
                .companyName("TechCorp Solutions")
                .title("Senior Full Stack Java Developer")
                .description("TechCorp Solutions is seeking a Senior Full Stack Java Developer to lead our next-generation cloud recruiting analytics platform. You will design, develop, and maintain resilient Spring Boot microservices and intuitive React frontends.")
                .requirements("5+ years experience in Java and Spring Boot. Strong proficiency with React.js, Redux or Context API. Deep understanding of MySQL, Hibernate ORM, and RESTful APIs.")
                .skillsRequired("Java, Spring Boot, React.js, MySQL, Spring Security, REST APIs")
                .location("Austin, TX")
                .employmentType(EmploymentType.FULL_TIME)
                .experienceRequired("5+ Years")
                .salaryMin(BigDecimal.valueOf(120000.00))
                .salaryMax(BigDecimal.valueOf(145000.00))
                .vacanciesCount(3)
                .deadline(LocalDate.now().plusMonths(2))
                .status(JobStatus.PUBLISHED)
                .build());

        Job job2 = jobRepository.save(Job.builder()
                .recruiter(recruiter1)
                .company(techCorp)
                .companyName("TechCorp Solutions")
                .title("Backend Java & Microservices Engineer")
                .description("Join our core banking integration team building high-throughput payment processing APIs and distributed message brokers with Spring Boot and Kafka.")
                .requirements("Solid grasp of Java 17+, concurrency, Spring Data JPA, MySQL tuning, and OAuth2/JWT security patterns.")
                .skillsRequired("Java 17, Spring Boot, Spring Data JPA, MySQL, Redis, Kafka")
                .location("Remote")
                .employmentType(EmploymentType.REMOTE)
                .experienceRequired("3-5 Years")
                .salaryMin(BigDecimal.valueOf(105000.00))
                .salaryMax(BigDecimal.valueOf(130000.00))
                .vacanciesCount(2)
                .deadline(LocalDate.now().plusMonths(1))
                .status(JobStatus.PUBLISHED)
                .build());

        Job job3 = jobRepository.save(Job.builder()
                .recruiter(recruiter2)
                .company(innovateX)
                .companyName("InnovateX Labs")
                .title("Lead React & Frontend Architect")
                .description("InnovateX Labs is looking for a Lead Frontend Engineer to architect responsive, high-performance dashboards, charting workflows, and real-time collaboration tools.")
                .requirements("Mastery of modern React, JSX, ES6+, state management, responsive CSS, and RESTful API integration.")
                .skillsRequired("React.js, JavaScript, HTML5, CSS3, Recharts, Bootstrap")
                .location("San Francisco, CA")
                .employmentType(EmploymentType.FULL_TIME)
                .experienceRequired("4+ Years")
                .salaryMin(BigDecimal.valueOf(130000.00))
                .salaryMax(BigDecimal.valueOf(160000.00))
                .vacanciesCount(1)
                .deadline(LocalDate.now().plusMonths(1))
                .status(JobStatus.PUBLISHED)
                .build());

        Job job4 = jobRepository.save(Job.builder()
                .recruiter(recruiter2)
                .company(innovateX)
                .companyName("InnovateX Labs")
                .title("DevOps & Cloud Reliability Engineer")
                .description("Automate CI/CD pipelines, manage Kubernetes clusters on AWS, and ensure 99.99% platform availability.")
                .requirements("Hands-on experience with AWS, Docker, Kubernetes, Terraform, Prometheus/Grafana monitoring.")
                .skillsRequired("AWS, Kubernetes, Docker, Terraform, CI/CD, Linux")
                .location("Remote")
                .employmentType(EmploymentType.REMOTE)
                .experienceRequired("4+ Years")
                .salaryMin(BigDecimal.valueOf(125000.00))
                .salaryMax(BigDecimal.valueOf(155000.00))
                .vacanciesCount(2)
                .deadline(LocalDate.now().plusMonths(2))
                .status(JobStatus.PUBLISHED)
                .build());

        Job job5 = jobRepository.save(Job.builder()
                .recruiter(recruiter1)
                .company(techCorp)
                .companyName("TechCorp Solutions")
                .title("Associate Quality Assurance Engineer")
                .description("Collaborate with developers to author robust automated test suites, API test scripts, and perform regression testing on mission-critical features.")
                .requirements("Basic Java knowledge, JUnit, Selenium WebDriver, Postman API testing, and understanding of CI/CD integration.")
                .skillsRequired("Java, Selenium, JUnit, Postman, SQL, Git")
                .location("Austin, TX")
                .employmentType(EmploymentType.FULL_TIME)
                .experienceRequired("1-2 Years")
                .salaryMin(BigDecimal.valueOf(70000.00))
                .salaryMax(BigDecimal.valueOf(85000.00))
                .vacanciesCount(2)
                .deadline(LocalDate.now().plusMonths(1))
                .status(JobStatus.PUBLISHED)
                .build());

        // 6. Create Applications
        Application app1 = applicationRepository.save(Application.builder()
                .job(job1)
                .candidate(candidate1)
                .resume(resume1)
                .coverLetter("Dear Hiring Manager, I am thrilled to apply for the Senior Full Stack Java Developer position at TechCorp. Having built enterprise Spring Boot and React applications for over 6 years, I am confident I can make an immediate impact on your platform architecture.")
                .status(ApplicationStatus.SHORTLISTED)
                .build());

        Application app2 = applicationRepository.save(Application.builder()
                .job(job2)
                .candidate(candidate1)
                .resume(resume1)
                .coverLetter("I am excited about TechCorp backend microservices initiatives. My background with high-volume database queries and JWT security fits this role perfectly.")
                .status(ApplicationStatus.UNDER_REVIEW)
                .build());

        Application app3 = applicationRepository.save(Application.builder()
                .job(job5)
                .candidate(candidate2)
                .resume(resume2)
                .coverLetter("I would love to bring my automated testing expertise to TechCorp. I have implemented comprehensive regression suites and API tests in past projects.")
                .status(ApplicationStatus.INTERVIEW_SCHEDULED)
                .build());

        Application app4 = applicationRepository.save(Application.builder()
                .job(job4)
                .candidate(candidate3)
                .resume(null)
                .coverLetter("My 7 years in cloud engineering, Kubernetes orchestration, and AWS infrastructure align directly with InnovateX mission to scale reliably.")
                .status(ApplicationStatus.APPLIED)
                .build());

        // 7. Create Interviews
        interviewRepository.save(Interview.builder()
                .application(app1)
                .recruiter(recruiter1)
                .candidate(candidate1)
                .interviewDate(LocalDate.now().plusDays(5))
                .interviewTime(LocalTime.of(14, 0))
                .interviewType(InterviewType.ONLINE)
                .meetingLink("https://meet.google.com/abc-defg-hij")
                .instructions("Technical Architecture & Coding Round. Please ensure your development environment with IDE and camera is ready.")
                .status(InterviewStatus.SCHEDULED)
                .build());

        interviewRepository.save(Interview.builder()
                .application(app3)
                .recruiter(recruiter1)
                .candidate(candidate2)
                .interviewDate(LocalDate.now().plusDays(3))
                .interviewTime(LocalTime.of(11, 0))
                .interviewType(InterviewType.ONLINE)
                .meetingLink("https://meet.google.com/xyz-uvwx-rst")
                .instructions("QA Automation & API testing discussion with the Lead SDET.")
                .status(InterviewStatus.SCHEDULED)
                .build());

        // 8. Create Notifications
        notificationRepository.save(Notification.builder()
                .user(candidate1)
                .title("Application Shortlisted!")
                .message("Congratulations! Your application for Senior Full Stack Java Developer at TechCorp Solutions has been shortlisted.")
                .type("APPLICATION_STATUS")
                .isRead(false)
                .linkUrl("/candidate/applications")
                .build());

        notificationRepository.save(Notification.builder()
                .user(candidate1)
                .title("Interview Scheduled")
                .message("An online technical interview has been scheduled for Senior Full Stack Java Developer.")
                .type("INTERVIEW_SCHEDULED")
                .isRead(false)
                .linkUrl("/candidate/interviews")
                .build());

        notificationRepository.save(Notification.builder()
                .user(recruiter1)
                .title("New Application Received")
                .message("John Doe applied for Senior Full Stack Java Developer.")
                .type("NEW_APPLICATION")
                .isRead(true)
                .linkUrl("/recruiter/jobs/" + job1.getId() + "/applicants")
                .build());

        log.info("JobTrack platform sample data seeded successfully!");
    }

    private void ensureDemoAccounts(String encodedPassword) {
        userRepository.findByEmail("admin@jobtrack.com").ifPresentOrElse(
                u -> {
                    u.setPassword(encodedPassword);
                    u.setStatus(UserStatus.ACTIVE);
                    userRepository.save(u);
                },
                () -> {
                    userRepository.save(User.builder()
                            .email("admin@jobtrack.com")
                            .password(encodedPassword)
                            .fullName("System Administrator")
                            .phone("+1-555-0100")
                            .role(Role.ROLE_ADMIN)
                            .status(UserStatus.ACTIVE)
                            .build());
                    log.info("Ensured demo admin account exists: admin@jobtrack.com");
                }
        );

        Company company = companyRepository.findAll().stream().findFirst().orElseGet(() ->
                companyRepository.save(Company.builder()
                        .name("TechCorp Solutions")
                        .description("Global enterprise cloud and software engineering powerhouse.")
                        .website("https://techcorp-example.com")
                        .industry("Information Technology")
                        .companySize("500-1000 employees")
                        .address("100 Innovation Way, Austin, TX")
                        .build())
        );

        userRepository.findByEmail("recruiter@jobtrack.com").ifPresentOrElse(
                u -> {
                    u.setPassword(encodedPassword);
                    u.setStatus(UserStatus.ACTIVE);
                    userRepository.save(u);
                },
                () -> {
                    User recruiter = userRepository.save(User.builder()
                            .email("recruiter@jobtrack.com")
                            .password(encodedPassword)
                            .fullName("Sarah Jenkins")
                            .phone("+1-555-0101")
                            .role(Role.ROLE_RECRUITER)
                            .status(UserStatus.ACTIVE)
                            .build());

                    recruiterProfileRepository.save(RecruiterProfile.builder()
                            .user(recruiter)
                            .company(company)
                            .designation("Lead Technical Recruiter")
                            .department("Talent Acquisition")
                            .contactPhone("+1-555-0101")
                            .build());
                    log.info("Ensured demo recruiter account exists: recruiter@jobtrack.com");
                }
        );

        userRepository.findByEmail("candidate@jobtrack.com").ifPresentOrElse(
                u -> {
                    u.setPassword(encodedPassword);
                    u.setStatus(UserStatus.ACTIVE);
                    userRepository.save(u);
                },
                () -> {
                    User candidate = userRepository.save(User.builder()
                            .email("candidate@jobtrack.com")
                            .password(encodedPassword)
                            .fullName("Alex Johnson")
                            .phone("+1-555-0200")
                            .role(Role.ROLE_CANDIDATE)
                            .status(UserStatus.ACTIVE)
                            .build());

                    candidateProfileRepository.save(CandidateProfile.builder()
                            .user(candidate)
                            .headline("Full Stack Software Engineer")
                            .bio("Full Stack Developer with expertise in React.js and Spring Boot.")
                            .location("San Francisco, CA")
                            .currentSalary(BigDecimal.valueOf(110000.00))
                            .expectedSalary(BigDecimal.valueOf(130000.00))
                            .experienceYears(4)
                            .skills("Java, Spring Boot, React.js, JavaScript, MySQL, Docker, REST APIs")
                            .build());
                    log.info("Ensured demo candidate account exists: candidate@jobtrack.com");
                }
        );

        // Also ensure legacy test accounts if they exist have Password@123
        userRepository.findByEmail("sarah.recruiter@techcorp.com").ifPresent(u -> {
            u.setPassword(encodedPassword);
            u.setStatus(UserStatus.ACTIVE);
            userRepository.save(u);
        });

        userRepository.findByEmail("john.dev@example.com").ifPresent(u -> {
            u.setPassword(encodedPassword);
            u.setStatus(UserStatus.ACTIVE);
            userRepository.save(u);
        });
    }
}
