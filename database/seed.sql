-- =============================================================================
-- JobTrack: Seed / Sample Data
-- Password for all test users: Password@123
-- BCrypt Hash: $2a$10$e7Q8WwBsqiM97sC7Zf7h0.5FjCj9K78M275X8wA/t0YnK3eL5v4fW (or Spring BCrypt equivalent)
-- =============================================================================

USE jobtrack_db;

-- 1. Users
-- password hash for 'Password@123'
INSERT INTO users (id, email, password_hash, full_name, phone, role, status) VALUES
(1, 'admin@jobtrack.com', '$2a$10$7v59n8N1H.rFm5Zvh81aKOWvY0pmsiM/30Evy2tK198a0Fh05wHxe', 'System Administrator', '+1-555-0100', 'ROLE_ADMIN', 'ACTIVE'),
(2, 'sarah.recruiter@techcorp.com', '$2a$10$7v59n8N1H.rFm5Zvh81aKOWvY0pmsiM/30Evy2tK198a0Fh05wHxe', 'Sarah Jenkins', '+1-555-0101', 'ROLE_RECRUITER', 'ACTIVE'),
(3, 'alex.talent@innovatex.io', '$2a$10$7v59n8N1H.rFm5Zvh81aKOWvY0pmsiM/30Evy2tK198a0Fh05wHxe', 'Alex Rivera', '+1-555-0102', 'ROLE_RECRUITER', 'ACTIVE'),
(4, 'john.dev@example.com', '$2a$10$7v59n8N1H.rFm5Zvh81aKOWvY0pmsiM/30Evy2tK198a0Fh05wHxe', 'John Doe', '+1-555-0201', 'ROLE_CANDIDATE', 'ACTIVE'),
(5, 'emily.qa@example.com', '$2a$10$7v59n8N1H.rFm5Zvh81aKOWvY0pmsiM/30Evy2tK198a0Fh05wHxe', 'Emily Watson', '+1-555-0202', 'ROLE_CANDIDATE', 'ACTIVE'),
(6, 'michael.cloud@example.com', '$2a$10$7v59n8N1H.rFm5Zvh81aKOWvY0pmsiM/30Evy2tK198a0Fh05wHxe', 'Michael Chang', '+1-555-0203', 'ROLE_CANDIDATE', 'ACTIVE'),
(7, 'recruiter@jobtrack.com', '$2a$10$7v59n8N1H.rFm5Zvh81aKOWvY0pmsiM/30Evy2tK198a0Fh05wHxe', 'Sarah Jenkins', '+1-555-0101', 'ROLE_RECRUITER', 'ACTIVE'),
(8, 'candidate@jobtrack.com', '$2a$10$7v59n8N1H.rFm5Zvh81aKOWvY0pmsiM/30Evy2tK198a0Fh05wHxe', 'Alex Johnson', '+1-555-0200', 'ROLE_CANDIDATE', 'ACTIVE');


-- 2. Companies
INSERT INTO companies (id, name, description, website, industry, company_size, logo_url, address) VALUES
(1, 'TechCorp Solutions', 'Global enterprise cloud and software engineering powerhouse delivering modern digital solutions.', 'https://techcorp-example.com', 'Information Technology', '500-1000 employees', 'https://images.unsplash.com/photo-1549923746-c502d488b3ea?w=128&h=128&fit=crop', '100 Innovation Way, Austin, TX'),
(2, 'InnovateX Labs', 'Next-generation AI and financial technology startup building scalable distributed systems.', 'https://innovatex-example.io', 'Fintech / AI', '50-200 employees', 'https://images.unsplash.com/photo-1572021335469-31706a17aaef?w=128&h=128&fit=crop', '452 Silicon Blvd, San Francisco, CA');

-- 3. Recruiter Profiles
INSERT INTO recruiter_profiles (id, user_id, company_id, designation, department, contact_phone) VALUES
(1, 2, 1, 'Senior Technical Talent Partner', 'Engineering Talent Acquisition', '+1-555-0101'),
(2, 3, 2, 'Head of People & Recruiting', 'Human Resources', '+1-555-0102'),
(3, 7, 1, 'Lead Technical Recruiter', 'Talent Acquisition', '+1-555-0101');


-- 4. Resumes
INSERT INTO resumes (id, candidate_id, original_filename, stored_filename, file_type, file_size, storage_path) VALUES
(1, 4, 'John_Doe_Senior_Java_Engineer.pdf', 'resume_4_john_doe_2026.pdf', 'application/pdf', 142580, 'uploads/resumes/resume_4_john_doe_2026.pdf'),
(2, 5, 'Emily_Watson_FullStack_QA.pdf', 'resume_5_emily_watson_2026.pdf', 'application/pdf', 128900, 'uploads/resumes/resume_5_emily_watson_2026.pdf'),
(3, 6, 'Michael_Chang_Cloud_DevOps.pdf', 'resume_6_michael_chang_2026.pdf', 'application/pdf', 156320, 'uploads/resumes/resume_6_michael_chang_2026.pdf');

-- 5. Candidate Profiles
INSERT INTO candidate_profiles (id, user_id, headline, bio, location, current_salary, expected_salary, experience_years, skills, education_json, experience_json, active_resume_id) VALUES
(1, 4, 'Senior Full Stack Java & React Developer', 'Passionate software craftsman with 6+ years building microservices with Spring Boot, Spring Security, Kafka, and modern React SPAs.', 'Austin, TX', 115000.00, 135000.00, 6, 'Java, Spring Boot, React, MySQL, Docker, Kubernetes, AWS, REST APIs, Microservices', 
'[{"degree":"B.S. in Computer Science","institution":"University of Texas at Austin","year":"2020","grade":"3.8 GPA"}]', 
'[{"title":"Full Stack Developer","company":"CloudTech Inc","period":"2021 - Present","description":"Led migration from monolith to Spring Boot microservices, decreasing latency by 35%."}]', 
1),
(2, 5, 'Senior Software Quality Engineer (SDET)', 'Automation QA engineer specializing in Selenium, Cypress, REST-Assured, JUnit, and CI/CD pipelines.', 'Chicago, IL', 95000.00, 110000.00, 4, 'Java, JUnit 5, Selenium, Cypress, TestNG, REST-Assured, Jenkins, Docker, Postman', 
'[{"degree":"B.E. in Software Engineering","institution":"University of Illinois","year":"2022","grade":"3.7 GPA"}]', 
'[{"title":"SDET Lead","company":"Fintech Corp","period":"2022 - Present","description":"Designed and executed end-to-end automated testing suites."}]', 
2),
(3, 6, 'Cloud Infrastructure & DevOps Engineer', 'Specialist in AWS, Kubernetes, Terraform, Spring Boot orchestration, and zero-downtime deployments.', 'Seattle, WA', 130000.00, 150000.00, 7, 'AWS, Kubernetes, Docker, Terraform, CI/CD, Java, Spring Cloud, Linux, Prometheus', 
'[{"degree":"M.S. in Information Systems","institution":"University of Washington","year":"2019","grade":"3.9 GPA"}]', 
'[{"title":"Staff DevOps Engineer","company":"ScaleSystems","period":"2020 - Present","description":"Automated AWS multi-region infrastructure with Terraform."}]', 
3),
(4, 8, 'Full Stack Software Engineer', 'Passionate developer building responsive React applications and Spring Boot backends.', 'San Francisco, CA', 110000.00, 130000.00, 4, 'Java, Spring Boot, React.js, JavaScript, MySQL, Docker, REST APIs',
'[{"degree":"B.S. in Computer Science","institution":"UC Berkeley","year":"2021","grade":"3.8 GPA"}]',
'[{"title":"Software Engineer","company":"Innovate Soft","period":"2021 - Present","description":"Built full stack web features and RESTful backend APIs."}]',
1);


-- 6. Jobs
INSERT INTO jobs (id, recruiter_id, company_id, title, company_name, description, requirements, skills_required, location, employment_type, experience_required, salary_min, salary_max, vacancies_count, deadline, status) VALUES
(1, 2, 1, 'Senior Full Stack Java Developer', 'TechCorp Solutions', 
'TechCorp Solutions is seeking a Senior Full Stack Java Developer to lead our next-generation cloud recruiting analytics platform. You will design, develop, and maintain resilient Spring Boot microservices and intuitive React frontends.',
'Requirements: 5+ years experience in Java and Spring Boot. Strong hands-on proficiency with React.js, Redux or Context API. Deep understanding of MySQL, Hibernate ORM, and RESTful API architecture. Experience with Docker and cloud deployments.',
'Java, Spring Boot, React.js, MySQL, Spring Security, REST APIs', 'Austin, TX', 'FULL_TIME', '5+ Years', 120000.00, 145000.00, 3, '2026-12-31', 'PUBLISHED'),

(2, 2, 1, 'Backend Java & Microservices Engineer', 'TechCorp Solutions',
'Join our core banking integration team building high-throughput payment processing APIs and distributed message brokers with Spring Boot and Kafka.',
'Requirements: Solid grasp of Java 17+, concurrency, Spring Data JPA, MySQL tuning, and OAuth2/JWT security patterns. Knowledge of Redis caching and message queues.',
'Java 17, Spring Boot, Spring Data JPA, MySQL, Redis, Kafka', 'Remote', 'REMOTE', '3-5 Years', 105000.00, 130000.00, 2, '2026-12-15', 'PUBLISHED'),

(3, 3, 2, 'Lead React & Frontend Architect', 'InnovateX Labs',
'InnovateX Labs is looking for a Lead Frontend Engineer to architect responsive, high-performance dashboards, charting workflows, and real-time collaboration tools.',
'Requirements: Mastery of modern React, JSX, ES6+, state management, responsive CSS/Tailwind, and RESTful API integration. Passion for sleek UX/UI aesthetics.',
'React.js, JavaScript, HTML5, CSS3, Recharts, Bootstrap', 'San Francisco, CA', 'FULL_TIME', '4+ Years', 130000.00, 160000.00, 1, '2026-11-30', 'PUBLISHED'),

(4, 3, 2, 'DevOps & Site Reliability Engineer', 'InnovateX Labs',
'We are looking for a DevOps Engineer to automate CI/CD pipelines, manage Kubernetes clusters on AWS, and ensure 99.99% platform availability.',
'Requirements: Hands-on experience with AWS, Docker, Kubernetes, Terraform, Prometheus/Grafana monitoring, and Spring Boot service deployment.',
'AWS, Kubernetes, Docker, Terraform, CI/CD, Linux', 'Remote', 'REMOTE', '4+ Years', 125000.00, 155000.00, 2, '2026-12-20', 'PUBLISHED'),

(5, 2, 1, 'Associate Quality Assurance Engineer', 'TechCorp Solutions',
'Collaborate with developers and product managers to author robust automation test suites, API test scripts, and perform regression testing on mission-critical features.',
'Requirements: Basic Java knowledge, JUnit, Selenium WebDriver, Postman API testing, and understanding of CI/CD integration.',
'Java, Selenium, JUnit, Postman, SQL, Git', 'Austin, TX', 'FULL_TIME', '1-2 Years', 70000.00, 85000.00, 2, '2026-12-25', 'PUBLISHED');

-- 7. Applications
INSERT INTO applications (id, job_id, candidate_id, resume_id, cover_letter, status, applied_at) VALUES
(1, 1, 4, 1, 'Dear Hiring Manager, I am thrilled to apply for the Senior Full Stack Java Developer position at TechCorp. Having built enterprise Spring Boot and React applications for over 6 years, I am confident I can make an immediate impact on your platform architecture.', 'SHORTLISTED', '2026-11-01 10:15:00'),
(2, 2, 4, 1, 'I am excited about TechCorp backend microservices initiatives. My background with high-volume database queries and JWT security fits this role perfectly.', 'UNDER_REVIEW', '2026-11-03 14:20:00'),
(3, 5, 5, 2, 'I would love to bring my automated testing expertise to TechCorp. I have implemented comprehensive regression suites and API tests in past projects.', 'INTERVIEW_SCHEDULED', '2026-11-02 09:30:00'),
(4, 4, 6, 3, 'My 7 years in cloud engineering, Kubernetes orchestration, and AWS infrastructure align directly with InnovateX mission to scale reliably.', 'APPLIED', '2026-11-04 16:45:00');

-- 8. Interviews
INSERT INTO interviews (id, application_id, recruiter_id, candidate_id, interview_date, interview_time, interview_type, meeting_link, location_address, instructions, status) VALUES
(1, 1, 2, 4, '2026-11-20', '14:00:00', 'ONLINE', 'https://meet.google.com/abc-defg-hij', NULL, 'Technical Architecture & Coding Round. Please ensure your development environment with IDE and camera is ready.', 'SCHEDULED'),
(2, 3, 2, 5, '2026-11-18', '11:00:00', 'ONLINE', 'https://meet.google.com/xyz-uvwx-rst', NULL, 'QA Automation & API testing discussion with the Lead SDET.', 'SCHEDULED');

-- 9. Notifications
INSERT INTO notifications (id, user_id, title, message, type, is_read, link_url) VALUES
(1, 4, 'Application Shortlisted!', 'Congratulations! Your application for Senior Full Stack Java Developer at TechCorp Solutions has been shortlisted.', 'APPLICATION_STATUS', FALSE, '/candidate/applications'),
(2, 4, 'Interview Scheduled', 'An online technical interview has been scheduled for Nov 20, 2026 at 2:00 PM for Senior Full Stack Java Developer.', 'INTERVIEW_SCHEDULED', FALSE, '/candidate/interviews'),
(3, 5, 'Interview Scheduled', 'Interview scheduled for Associate Quality Assurance Engineer on Nov 18, 2026 at 11:00 AM.', 'INTERVIEW_SCHEDULED', FALSE, '/candidate/interviews'),
(4, 2, 'New Application Received', 'John Doe applied for Senior Full Stack Java Developer.', 'NEW_APPLICATION', TRUE, '/recruiter/jobs/1/applicants');
