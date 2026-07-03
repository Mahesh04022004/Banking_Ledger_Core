# Banking Core Ledger

A production-inspired backend banking application built with **Java 21**, **Spring Boot**, and **MySQL**.

This project demonstrates how modern banking systems manage accounts, money transfers, and immutable financial records using a **Double-Entry Ledger** architecture.

> **Project Status:** Phase 4.1 Completed (Ledger Module + Statement APIs)

---

# Features

## Account Management

* Create Account
* Get Account by ID
* Get All Accounts

## Banking Operations

* Deposit Money
* Withdraw Money
* Transfer Money between Accounts

## Double Entry Ledger

Every financial operation creates immutable ledger records.

Supported operations:

* Deposit
* Withdrawal
* Transfer (Debit + Credit entries)

## Statement APIs

* View Account Statement
* View Transaction Details using Transaction Reference

## Common Features

* Global API Response
* Global Exception Handling
* Validation
* Transaction Management
* Clean Layered Architecture
* DTO Mapping

---

# Tech Stack

| Technology      | Version |
| --------------- | ------- |
| Java            | 21      |
| Spring Boot     | 3.x     |
| Spring Data JPA | Latest  |
| MySQL           | 8+      |
| Maven           | Latest  |

---

# Project Structure

```
src/main/java

├── config
├── constants
├── controller
├── dto
│   ├── request
│   └── response
├── entity
├── enums
├── exception
├── mapper
├── repository
├── response
├── service
│   └── impl
├── util
└── BankingCoreApplication
```

---

# Prerequisites

Install:

* Java 21
* Maven
* MySQL

---

# Database Setup

Create a database.

```sql
CREATE DATABASE banking_core;
```

---

# Configure Database

Update `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/banking_core
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# Run the Project

Clone the repository.

```bash
git clone <repository-url>
```

Go inside the project.

```bash
cd backend/banking-core
```

Run

```bash
mvn spring-boot:run
```

or

```bash
./mvnw spring-boot:run
```

Application starts on

```
http://localhost:8081
```

---

# API Endpoints

## Account APIs

### Create Account

```
POST /api/v1/accounts
```

Example Request

```json
{
  "customerName": "Mahesh",
  "email": "mahesh@example.com",
  "initialBalance": 10000
}
```

---

### Get Account

```
GET /api/v1/accounts/{id}
```

---

### Get All Accounts

```
GET /api/v1/accounts
```

---

## Deposit

```
POST /api/v1/accounts/{id}/deposit
```

Example

```json
{
  "amount": 5000
}
```

---

## Withdraw

```
POST /api/v1/accounts/{id}/withdraw
```

Example

```json
{
  "amount": 2000
}
```

---

## Transfer Money

```
POST /api/v1/transfers
```

Example

```json
{
  "fromAccountNumber": "ACC00000001",
  "toAccountNumber": "ACC00000002",
  "amount": 1000
}
```

---

## Account Statement

```
GET /api/v1/ledger/accounts/{accountNumber}/statement
```

---

## Transaction Details

```
GET /api/v1/ledger/transactions/{transactionReference}
```

---

# Sample Flow

### Step 1

Create two accounts.

```
Mahesh
Balance : 10000
```

```
Rahul
Balance : 5000
```

### Step 2

Transfer

```
1000

Mahesh → Rahul
```

### Step 3

Check balances.

Mahesh

```
9000
```

Rahul

```
6000
```

### Step 4

Fetch statement.

```
GET /api/v1/ledger/accounts/ACC00000001/statement
```

The response will contain the debit ledger entry.

### Step 5

Fetch transaction.

```
GET /api/v1/ledger/transactions/{transactionReference}
```

The response will contain both the debit and credit entries for the transfer.

---

# Current Architecture

```
Controller
        │
        ▼
Service
        │
        ▼
Repository
        │
        ▼
MySQL
```

Money transfer flow:

```
Transfer Request
        │
        ▼
Business Validation
        │
        ▼
Update Account Balances
        │
        ▼
Persist Ledger Entries
        │
        ▼
Return Response
```

---

# Current Limitations

This project is still under active development.

Upcoming features include:

* Concurrency Handling
* Optimistic Locking
* Pessimistic Locking
* Redis Distributed Lock (Redisson)
* Kafka Event Publishing
* Unit Testing
* Integration Testing
* Swagger / OpenAPI Documentation

---

# Version

Current Version

```
v0.5.0
```

---

# License

This project is intended for learning, portfolio demonstration, and backend architecture practice.
