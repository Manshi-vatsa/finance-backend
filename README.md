# Finance Backend System

## 📌 Project Overview
This is a Spring Boot backend system designed to manage users, roles, and financial records with role-based access control.

## 🛠 Tech Stack
- Java
- Spring Boot
- Spring Security
- H2 Database
- JWT Authentication

## 🚀 Features
- User Management (Create, Update, Delete)
- Role-Based Access (Admin, Analyst, Viewer)
- Dashboard Data Access
- Secure APIs using JWT
- Input Validation & Error Handling

## 🔐 Role Access
- Admin → Full access
- Analyst → View + analyze data
- Viewer → Only dashboard view

## 📡 API Endpoints
- POST /auth/login
- POST /users/add
- GET /dashboard
- etc...

## ⚙️ How to Run
1. Clone repo
2. Open in IntelliJ
3. Run application
4. Open H2 console: /h2-console

## 📌 Assumptions
- JWT used for authentication
- In-memory DB used for simplicity