# 🏦 Banking Core Ledger

A production-inspired Banking Core Ledger built with **Spring Boot**, following enterprise backend design principles such as **event-driven architecture**, **double-entry ledger**, **distributed locking**, and **Apache Kafka**.

This project is designed as a backend portfolio project to demonstrate real-world banking concepts beyond CRUD applications.

---

# ✨ Features

## Account Management

- Create Account
- View Account
- View All Accounts
- Deposit Money
- Withdraw Money
- Transfer Money

---

## Ledger

Every balance-changing operation creates an immutable ledger entry.

Supported operations:

- Deposit
- Withdraw
- Transfer (Debit + Credit)

Each ledger entry stores:

- Transaction Reference
- Entry Type
- Amount
- Balance Before
- Balance After
- Timestamp

---

## Event-Driven Architecture

Every successful transaction publishes a Kafka event.

Supported events:

- Deposit
- Withdraw
- Transfer

Consumers currently implemented:

- Audit Consumer

---

## Distributed Locking

Uses **Redis + Redisson** to prevent race conditions during concurrent transactions.

Supports:

- Safe Deposit
- Safe Withdraw
- Safe Transfer

---

## Kafka

Uses Apache Kafka for asynchronous event publishing.

Current implementation includes:

- Kafka Producer
- Kafka Consumer
- Producer Callbacks
- Retry Support
- Dead Letter Topic (DLT)

---

# 🏗 Architecture

```
                    REST API
                        │
                        ▼
                 Service Layer
                        │
        ┌───────────────┼───────────────┐
        ▼               ▼               ▼
   Account         Ledger         Event Publisher
 Repository       Repository            │
                                        ▼
                                     Kafka Topic
                                        │
                              ┌─────────┴─────────┐
                              ▼                   ▼
                       Audit Consumer       Future Consumers
```

---

# 🛠 Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 21 | Programming Language |
| Spring Boot | Backend Framework |
| Spring Data JPA | Database Access |
| MySQL | Database |
| Redis | Distributed Locking |
| Redisson | Distributed Lock Implementation |
| Apache Kafka | Event Streaming |
| Docker Compose | Local Infrastructure |
| Maven | Dependency Management |
| Swagger/OpenAPI | API Documentation |

---

# 📁 Project Structure

```
backend/
 └── banking-core
      ├── controller
      ├── service
      ├── repository
      ├── entity
      ├── dto
      ├── mapper
      ├── ledger
      ├── event
      │     ├── producer
      │     ├── consumer
      │     ├── mapper
      │     └── model
      ├── config
      ├── util
      └── exception
```

---

# ⚙ Prerequisites

Install:

- Java 21
- Maven
- Docker Desktop
- MySQL 8+

---

# 🚀 Running Locally

## 1. Clone Repository

```bash
git clone https://github.com/Mahesh04022004/Banking_Ledger_Core.git

cd Banking_Ledger_Core/backend/banking-core
```

---

## 2. Create Database

```sql
CREATE DATABASE banking_core;
```

---

## 3. Configure Database

Update:

```
application.properties
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/banking_core

spring.datasource.username=YOUR_USERNAME

spring.datasource.password=YOUR_PASSWORD
```

---

## 4. Start Redis & Kafka

```bash
docker compose up -d
```

Verify:

```bash
docker ps
```

Expected:

```
banking-redis

banking-kafka
```

---

## 5. Run Spring Boot

```bash
mvn spring-boot:run
```

or

Run `BankingCoreApplication` from your IDE.

---

# 📚 Swagger

After the application starts:

```
http://localhost:8081/swagger-ui/index.html
```

OpenAPI JSON:

```
http://localhost:8081/v3/api-docs
```

---

# 📬 API Overview

## Account

| Method | Endpoint |
|---------|----------|
| POST | /api/accounts |
| GET | /api/accounts |
| GET | /api/accounts/{id} |

---

## Deposit

| Method | Endpoint |
|---------|----------|
| POST | /api/accounts/{id}/deposit |

---

## Withdraw

| Method | Endpoint |
|---------|----------|
| POST | /api/accounts/{id}/withdraw |

---

## Transfer

| Method | Endpoint |
|---------|----------|
| POST | /api/transfers |

---

# 🔁 Transaction Flow

```
Client

   │

   ▼

Spring Boot API

   │

   ▼

Distributed Lock (Redis)

   │

   ▼

Update Database

   │

   ▼

Create Ledger Entry

   │

   ▼

Publish Kafka Event

   │

   ▼

Audit Consumer
```

---

# 🔒 Distributed Locking

Redisson locks ensure that concurrent requests cannot update the same account simultaneously.

Benefits:

- Prevents race conditions
- Prevents lost updates
- Ensures balance consistency

---

# 📡 Kafka Events

Every successful transaction publishes:

```json
{
  "transactionReference": "TRX123456",
  "eventType": "TRANSFER",
  "fromAccount": "ACC000001",
  "toAccount": "ACC000002",
  "amount": 500,
  "timestamp": "2026-07-03T12:30:45"
}
```

---

# 🔄 Retry & Dead Letter Topic

Kafka consumers support:

- Automatic Retry
- Exponential Backoff
- Dead Letter Topic

Failed messages are redirected to:

```
transaction-events-dlt
```

---

# 🧪 Testing

Example Transfer:

```http
POST /api/transfers
```

```json
{
  "fromAccountNumber": "ACC000001",
  "toAccountNumber": "ACC000002",
  "amount": 500
}
```

After execution:

- Account balance updated
- Ledger entries created
- Kafka event published
- Audit consumer processes event

---

# 🚀 Future Enhancements

- Transactional Outbox Pattern
- Notification Service
- Fraud Detection Service
- Analytics Consumer
- JWT Authentication
- Prometheus Metrics
- Grafana Dashboard
- Kubernetes Deployment
- CI/CD Pipeline
- Integration Tests
- Testcontainers
- Multi-Broker Kafka Cluster

---

# 👨‍💻 Author

**Mahesh**

GitHub:

https://github.com/Mahesh04022004

---

# 📄 License

This project is intended for educational and portfolio purposes.
