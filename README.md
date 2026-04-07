# BillCore – Subscription & Billing API

## 📌 Overview

**BillCore** is a backend API for managing users, subscriptions, and invoices.
It is a simulation of a billing system with domain-driven design principles, relational data modelling, and production-like architecture.

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway (DB migrations)**
- **Docker & docker-compose**
- **Hibernate Validator**
- **Springdoc OpenAPI (Swagger)**

---

## 📦 Features

### 👤 Users

- Create users
- Fetch all users / by ID
- Delete users
- Unique email validation

---

### 🔁 Subscriptions

- Create subscription for a user
- Cancel subscription
- Fetch subscription by ID
- Subscription lifecycle management:
  - `ACTIVE`
  - `CANCELLED`

---

### 💸 Invoices

- Automatically generated on subscription creation
- Linked to a subscription
- Includes billing period:
  - `periodStart`
  - `periodEnd`

- Stores amount and creation timestamp

---

## 🧠 Domain Design

- A **User** can have multiple **Subscriptions**
- A **Subscription** belongs to exactly one **User**
- An **Invoice** belongs to a **Subscription**
- Business rules are enforced in the service layer

---

## 🌐 API Endpoints

### Users

```
POST   /users
GET    /users
GET    /users/{id}
DELETE /users/{id}
```

### Subscriptions

```
POST   /subscriptions
GET    /subscriptions/{id}
POST   /subscriptions/{id}/cancel
DELETE /subscriptions/{id}
```

### Invoices

```
GET /invoices?userId=
```

---

## 📥 Example Requests

### Create User

```json
{
  "email": "test@example.com"
}
```

### Create Subscription

```json
{
  "userId": 1
}
```

---

## ⚠️ Error Handling

The API uses a centralized exception handler and returns structured error responses:

```json
{
  "timestamp": "2026-04-07T12:00:00",
  "status": 400,
  "error": "VALIDATION_ERROR",
  "message": "Invalid request data",
  "errors": [
    {
      "field": "email",
      "message": "must be a well-formed email address"
    }
  ]
}
```

---

## 🗄️ Database

- Managed via **Flyway migrations**
- Schema includes:
  - users
  - subscriptions
  - invoices

- Uses foreign keys for integrity

---

## 🐳 Running the Project

### 1. Start PostgreSQL

```bash
docker-compose up -d
```

### 2. Run the application

```bash
./mvnw spring-boot:run
```

---

## 🧪 Testing (Yet to be made)

- Unit tests for service layer
- Integration tests for API + database (planned / optional)
