# Courses API - Backend Application

This Spring Boot application provides RESTful APIs for managing courses and their delivery instances. It was developed as part of the internship assignment for the Application Software Centre, IIT Bombay.

---

## 🔧 Features

- Create, view, and delete **courses** with **prerequisite validation**.
- Manage **course delivery instances** by year and semester.
- Enforce deletion constraints for courses that are prerequisites.
- Use of **Spring Data JPA** and **MySQL** for persistence.
- Includes **CORS configuration** to allow cross-origin requests from the frontend.

---

## 📁 Project Structure

- `Course`: Entity representing a course.
- `CourseInstance`: Entity representing a course instance (year, semester).
- `CourseRepository`, `CourseInstanceRepository`: Spring Data JPA repositories.
- `CourseController`, `CourseInstanceController`: REST API controllers.
- `CorsConfig`: Allows frontend access on specified port (default: `5173`).
- `application-docker.properties`: Used when running via Docker.

---

## 🚀 REST API Endpoints

### Course Management

- `POST /api/courses`: Create a new course with optional prerequisites.
- `GET /api/courses`: Get list of all courses with their prerequisites.
- `GET /api/courses/{id}`: Get details of a single course.
- `DELETE /api/courses/{id}`: Delete a course if it's not a prerequisite for another.

### Course Instance Management

- `POST /api/instances`: Create a course delivery instance.
- `GET /api/instances/{year}/{semester}`: Get all course instances for a year and semester.
- `GET /api/instances/{year}/{semester}/{courseId}`: View details of a course instance.
- `DELETE /api/instances/{year}/{semester}/{courseId}`: Delete a course instance.

---

## 🐳 Docker Setup

### Docker Image

This project is containerized and available on Docker Hub:

docker pull samiranrai/iitb-courses-app-course-api


### Build Locally

```bash
docker build -t samiranrai/iitb-courses-app-course-api .
Run Locally
Make sure MySQL is running (you can use Docker Compose):

docker run -p 8080:8080 samiranrai/iitb-courses-app-course-api
🧾 Docker Compose (Backend + Frontend + DB)

If you're using this service with frontend and MySQL, place the docker-compose.yml file at the root and run:

docker-compose up
```
🛠 Tech Stack

Java 22
Spring Boot 3.5
Hibernate / JPA
MySQL 8
Docker
📝 Notes

CORS is enabled for http://localhost:5173 to support local frontend development.
Uses Spring Boot Profiles (docker) for environment-specific configs.
Deletion of courses is blocked if they are prerequisites to ensure data integrity.
📬 Contact

For any issues or suggestions, feel free to raise an issue on GitHub.