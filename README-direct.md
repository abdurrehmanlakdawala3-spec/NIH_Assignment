# Quick start

1. Clone the repo and open the project root:

```powershell
git clone <your-repo-url>
cd NIH_Assignment
```
---

## Backend (Spring Boot — Java 21)

### Prerequisites

- Java 21
- Gradle 8+
- PostgreSQL (or another DB configured via `application.yml`)
- IDE (IntelliJ / VS Code) or terminal access

1. Go to BE directory

```powershell
cd NIH_Assignment_BE
```

2. Configure the database (example `application.yml` / `application.properties`): **Optional** (this project have embedded H2 database)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/student_db
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

3. Run the application

- From your IDE: run the main class `NIHAssignmentApplication`.
- From terminal (project root):

```powershell
./gradlew bootRun
```

The backend API will be available at: http://localhost:8080

Open Swagger / OpenAPI UI to explore endpoints: http://localhost:8080/swagger-ui/index.html

---

## Frontend (Angular)

### Prerequisites

- Node.js 18+
- Angular CLI 16+
- Backend running (see above)

### Quick start

1. Change into the frontend folder and install dependencies:

```powershell
cd NIH_Assignment_UI
npm install
```

2. Start the Angular app:

```powershell
npm run local
```

The frontend runs at: http://localhost:4200

---

## Code quality & reports

Run checks and view reports from the project root.

- Checkstyle

```powershell
./gradlew checkstyleMain
./gradlew checkstyleTest
```

Report: `build/reports/checkstyle/`

- PMD

```powershell
./gradlew pmdMain
./gradlew pmdTest
```

Report: `build/reports/pmd/`

- Tests + JaCoCo

```powershell
./gradlew test
./gradlew jacocoTestReport
```

HTML coverage: `build/reports/jacoco/test/html/index.html`

- Run all checks

```powershell
./gradlew clean check
```

This runs tests, Checkstyle, PMD, and coverage checks.

---

## Important URLs

- Angular UI: http://localhost:4200
- Backend API base: http://localhost:8080/api
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- JaCoCo report: `build/reports/jacoco/test/html/index.html`
- PMD report: `build/reports/pmd`
- Checkstyle report: `build/reports/checkstyle`

---

## Recommended order to start

1. Start your database (PostgreSQL) ** Optional**
2. Start backend: `./gradlew bootRun` (from `NIH_Assignment_BE`)
3. Start frontend: `ng serve` (from `NIH_Assignment_UI`)
4. Open the UI at `http://localhost:4200`

---

## Tech stack

- Backend: Java 21, Spring Boot 3.x, Spring Data JPA, Hibernate
- QA: Checkstyle, PMD, JaCoCo
- Frontend: Angular 16+, Angular Material, RxJS, Reactive Forms

---
