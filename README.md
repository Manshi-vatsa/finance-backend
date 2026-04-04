# Finance Backend System

## 📌 Overview

A secure Spring Boot backend for managing users and financial data with role-based access control (RBAC). Designed to demonstrate clean architecture, authentication, and API design.

---

## 🛠 Tech Stack

* Java, Spring Boot
* Spring Security + JWT
* Spring Data JPA
* H2 Database
* Maven

---

## 🚀 Features

* User Authentication (JWT)
* Role-Based Access (Admin, Analyst, Viewer)
* Secure REST APIs
* Dashboard APIs (Insights, Summary)
* Pagination
* Validation & Global Exception Handling

---

## 🔐 Roles

* **Admin** → Full access
* **Analyst** → Insights + Dashboard
* **Viewer** → Dashboard only

---

## 📡 Key APIs

* `POST /users/login`
* `POST /users/create`
* `GET /users/dashboard`
* `GET /users/insights`
* `GET /users?page=0&size=5`

---

## ⚙️ Run Project

```bash
mvn spring-boot:run
```

H2 Console:
`http://localhost:8080/h2-console`
JDBC: `jdbc:h2:mem:testdb`

---

## 🔑 Auth Flow

1. Create first Admin user
2. Login → get JWT
3. Use token in headers:
   `Authorization: Bearer <token>`

---

## 📌 Highlights

* Clean layered architecture
* Secure authentication & authorization
* Production-level API practices

---
