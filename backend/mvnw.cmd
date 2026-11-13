@echo off
set "MAVEN_CMD=D:\tools\apache-maven-3.9.9\bin\mvn.cmd"
if exist "%MAVEN_CMD%" (
    call "%MAVEN_CMD%" %*
) else (
    mvn %*
)
