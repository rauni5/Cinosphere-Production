# Cinosphere - Cinema Management System (Modern Version)

## Overview

Cinosphere is a modern cinema management system built using **Spring Boot (REST API)** and **React.js** for the frontend. It is an upgraded evolution of the original Java Servlet-based application, redesigned with a scalable, API-driven architecture.

This version focuses on separation of concerns, improved performance, and a more responsive user experience through a modern frontend-backend decoupled system.

Original legacy version:  
[Legacy](https://github.com/rauni5/Cinosphere-Production/tree/legacy-servlet) (Servlet + Tomcat version)

Legacy coursework reference:  
[Original-Repo](https://github.com/Adii-r/Advance-Java-Coursework)

---

## Features

- User authentication & role-based access control
- Movie management system (CRUD operations)
- Cinema scheduling and showtime management
- Online ticket booking system
- Seat selection and availability tracking
- Customer profile management
- Admin dashboard for system control
- RESTful API architecture
- Responsive and dynamic frontend UI

---

## Technologies Used

### Backend

- Java 17+
- Spring Boot
- Spring Web (REST API)
- Spring Data JPA
- Hibernate
- Maven
- JWT Authentication (if implemented)

### Frontend

- React.js
- JavaScript
- HTML / CSS
- Axios (API communication)
- React Router

### Database

- MySQL

### Server / Deployment

- Embedded Tomcat (Spring Boot)
- Docker

---

## Architecture

This system follows a **modern layered architecture** with clear separation between frontend and backend:

### Backend (Spring Boot API)
- Controller Layer → Handles HTTP requests (REST endpoints)
- Service Layer → Business logic
- Repository Layer → Database interaction (JPA/Hibernate)
- Model Layer → Entity definitions

### Frontend (React SPA)
- Component-based UI
- State management for dynamic data
- API integration via Axios
- Client-side routing

### Communication
- RESTful API (JSON-based communication between frontend and backend)

---

## Learning Outcomes

Through this modern rewrite, I gained experience in:

- Building RESTful APIs using Spring Boot
- Designing scalable backend architecture
- React.js component-based development
- API integration between frontend and backend
- Authentication and authorization concepts (JWT / session-based)
- Migrating from monolithic MVC to decoupled architecture
- Improving code maintainability and modular design

---

## Migration Note

This project is a **complete architectural migration** from a monolithic Servlet-based system to a modern **REST + SPA architecture**.  
Both versions are maintained separately for learning, comparison, and portfolio purposes.

---

## Author

Raunit Giri  
BSc (Hons) Computing Student
