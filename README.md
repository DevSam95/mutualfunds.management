# Project Overview

## Tech Stack
o	Backend: Java 24 and Spring Boot 3.5.7
o	Database: Spring Data JPA w4ith MySQL
o	Authentication: Spring Security for Basic Authentication.
o	Build Tool: Maven
<!-- o	Testing: JUnit 5, Mockito, and Spring Boot Test. -->

## Setup Instructions

### Database config

For the purpose of this POC, I have used System env variables

In windows PowerShell it can be set as follows

$env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/mutualfund_management"
$env:SPRING_DATASOURCE_USERNAME="root"
$env:SPRING_DATASOURCE_PASSWORD="root"

### Build and run

- Execute `mvnw` or `mvnw.cmd`
- Alternatively run directly using VSCode
