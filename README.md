# Employee Management System

A backend application built using Java and Spring Boot to manage employee records with secure authentication, role-based authorization, REST APIs, and MySQL database integration.

## Technologies Used

- Java 17
- Spring Boot 3.5.16
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- JUnit 5
- Mockito
- Bean Validation

## Features

### Employee Management
- Create employee records
- Retrieve employee details
- Update employee information
- Delete employee records
- Search employees
- Pagination and sorting
- Department-based search
- Employee salary and department statistics

### Security
- User registration and login
- JWT-based authentication
- Role-based authorization
- ADMIN-level access control
- Password encryption

### Exception Handling and Validation
- Global exception handling
- Request validation
- Custom employee-not-found exception
- Standard API response structure

### Logging
- Aspect-oriented programming (AOP)
- Method execution logging
- Execution time tracking

### Testing
- Service-layer unit tests
- Controller-layer tests
- JUnit 5 and Mockito

## Project Structure

```text
src/main/java/com/badri/employee_management_system
│
├── Config
├── Controller
├── Dto
├── Entity
├── Exception
├── Repository
├── Security
└── Service
    └── Impl
```

## Configuration

Configure the following environment variables before running the application:

| Variable | Description |
|---|---|
| DB_URL | MySQL database URL |
| DB_USERNAME | MySQL username |
| DB_PASSWORD | MySQL password |
| JWT_SECRET | JWT signing secret |
| APP_ADMIN_USERNAME | Initial admin username |
| APP_ADMIN_EMAIL | Initial admin email |
| APP_ADMIN_PASSWORD | Initial admin password |

The application runs on port `8083` by default.

## Run the Application

Clone the repository:

```bash
git clone https://github.com/viratbadri214/Employee-Management-System.git
```

Navigate to the project directory:

```bash
cd Employee-Management-System
```

Run the application using Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

## Run Tests

```powershell
.\mvnw.cmd clean test
```

## Author

GitHub: [viratbadri214](https://github.com/viratbadri214)