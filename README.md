# Project Overview

## Tech Stack
- Backend: Java 24 and Spring Boot 3.5.7
- Database: Spring Data JPA w4ith MySQL
- Authentication: Spring Security for Basic Authentication.
- Build Tool: Maven
<!-- o	Testing: JUnit 5, Mockito, and Spring Boot Test. -->

## Setup Instructions

### Database config

For the purpose of this POC, I have used System env variables

In windows PowerShell it can be set as follows

- $env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/mutualfund_management"
- $env:SPRING_DATASOURCE_USERNAME="root"
- $env:SPRING_DATASOURCE_PASSWORD="root"

### Build
- Execute `mvn clean install` to build the pacakge

### Run
- Execute `mvn spring-boot:run` to run the application locally

### Tests
- Execute `mvn clean test` to run the test cases
- The coverage report can be found at `target/site/jacoco/index.html`

## Dependencies

- The project used Java 21 as its the latest stable version with LTS
- Maven is used as dependecy management tool as its familiar to me
- Spring starter dependencies are included for testing, security, jpa and aop
- MySQL is used as the relational database
