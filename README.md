# Login Service (Desafio)

## Overview
Serviço de Login para o desafio 

## Features
- RESTful API support
- Dependency Injection
- Embedded Tomcat server
- Spring Data JPA for database interactions
- Spring Security for authentication and authorization

## Prerequisites
- Java 17 or higher
- Maven

## Getting Started

### Build the Application
Using Maven:
```bash
mvn clean install
```
Using Gradle:
```bash
./gradlew build
```

### Run the Application
Using Maven:
```bash
mvn spring-boot:run
```
Using Gradle:
```bash
./gradlew bootRun
```

### Access the Application
Open your web browser and navigate to: http://localhost:8889

### Observation
The project needs a .env file on the root and on the resources folder of the project, with the following template
```
SECRET_KEY=
ISSUER=
```
