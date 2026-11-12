@echo off
echo ===================================================
echo Starting JobTrack Backend (Spring Boot 3 on Port 8080)
echo ===================================================
cd /d "%~dp0backend"
set PATH=D:\tools\apache-maven-3.9.9\bin;%PATH%
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
pause
