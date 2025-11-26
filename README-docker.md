# Student Management Application — Docker

This repository contains a full-stack Student Management application and a Docker Compose setup to run both backend and frontend locally.

Components
- Backend: Java 17 + Spring Boot + Gradle (uses H2 by default for development)
- Frontend: Angular (project uses Angular + Angular Material)
- Database: H2 (development)
- Dockerized: Docker & Docker Compose (nginx for routing)

Features
- Manage Students, Courses, and Enrollments (CRUD)
- Pagination, search, and validation

## Prerequisites

- Docker Desktop (includes Docker Engine)
- Docker Compose (usually included with Docker Desktop)
- Optional: git (to clone the repo)

## Project layout

```
NIH_Assignment/
├─ NIH_Assignment_BE/        # Spring Boot Backend
├─ NIH_Assignment_UI/        # Angular Frontend
├─ docker-compose.yml       # Docker Compose configuration
└─ README-docker.md         # This fileconfiguration
└─ .....                      # Other files
```

## Run with Docker

Open a PowerShell terminal, clone the repo and open the project root:

```powershell
git clone https://github.com/abdurrehmanlakdawala3-spec/NIH_Assignment.git
cd NIH_Assignment
```

Build the images (only needed after changes or first run):

```powershell
docker-compose build
```

Start the services in the background:

```powershell
docker-compose up -d
```

What starts
- Backend container (e.g. `student-backend`) — port 8080
- Frontend container (e.g. `student-frontend`) — port 4200

Access the app
- Frontend (Angular UI): http://localhost:4200
- Backend Swagger UI: http://localhost:8080/swagger-ui/index.html


## Stop & clean

Stop containers:

```powershell
docker-compose down
```

Stop and remove images + volumes (clean start):

```powershell
docker-compose down --rmi all -v
```

## Troubleshooting

- If ports 8080 or 4200 are already in use, stop the conflicting service or update `docker-compose.yml` to map to different host ports.

## Quick checklist

1. Docker Desktop running
2. From repo root: `docker-compose build`
3. Start: `docker-compose up -d`
4. Open: http://localhost:4200 and http://localhost:8080/swagger-ui/index.html

---
