# 💸 SplitApp - Expense Splitter

🌐 **Live Deployment:** [https://splitapp-production.up.railway.app](https://splitapp-production.up.railway.app)

A backend API service built with **Spring Boot** and **PostgreSQL** to help groups split and settle expenses fairly. Perfect for roommates, trips, or any group activity.

---

## 🚀 Features

- ➕ Add people and expenses
- 📊 Split expenses (equal, exact, percent)
- 💰 View per-person balances
- 🔄 Optimized settlement calculation
- 📡 RESTful APIs with DTOs and validation

---

## 🔧 Tech Stack

| Component   | Technology          |
|------------|---------------------|
| Backend    | Spring Boot 3 (Java 17) |
| Database   | PostgreSQL          |
| ORM        | Hibernate (JPA)     |
| Build Tool | Maven               |
| Deployment | Railway             |
| Testing    | Postman             |

---

## 🏁 Getting Started

### ✅ Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL
- Git

---

### ⚙️ Local Setup Instructions

```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/SplitApp.git
cd SplitApp

# Set up environment variables (or use application.properties directly)
cp .env.example .env

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

📍 App runs at: [http://localhost:8080](http://localhost:8080)

---

## 🗂️ API Endpoints

> 📦 Full Postman Collection: `Expense Splitter APIs.postman_collection.json`

| Method | Endpoint      | Description          |
|--------|---------------|----------------------|
| GET    | `/people`     | List all persons     |
| POST   | `/people`     | Add a new person     |
| POST   | `/expenses`   | Add a new expense    |
| GET    | `/balances`   | View balances        |
| GET    | `/settlements`| Optimized settlements|

---

## 🗃️ Database Schema

Tables auto-created via JPA:

- **person**
- **expense**
- **expense_share**

### 🔍 Entity Overview

```java
@Entity
class Person {
  Long id;
  String name;
}

@Entity
class Expense {
  Long id;
  BigDecimal amount;
  Person paidBy;
  LocalDateTime createdAt;
  List<ExpenseShare> shares;
}

@Entity
class ExpenseShare {
  Long id;
  Expense expense;
  Person person;
  ShareType shareType; // EQUAL | EXACT | PERCENT
  BigDecimal value;
}
```

To set up the schema manually (if needed):
```sql
CREATE TABLE person (...);
CREATE TABLE expense (...);
CREATE TABLE expense_share (...);
```

---

## ⚙️ Configuration

Sample `application.properties` for local:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/splitapp
spring.datasource.username=your_pg_user
spring.datasource.password=your_pg_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
For Railway: use environment variables in the Railway dashboard.

---

## 🧮 Settlement Logic Explained

The system distributes the total expense among participants based on the selected sharing mode:
- **EQUAL**: Equal division
- **EXACT**: Fixed amount per person
- **PERCENT**: Percentage-based contribution

Balances are then calculated per person, and net dues are minimized using a greedy algorithm to settle debts optimally.

---

## 📌 Known Limitations

- No user authentication (yet)
- No frontend UI (Postman recommended)
- Limited validation on deployed version (local worked better)

---

## ✅ TODOs / Future Enhancements

- 🔐 Add JWT-based authentication
- 📤 Export settlements (PDF/CSV)
- 🌐 React-based frontend
- 🛠️ Flyway DB migration support

---

## 👨‍💻 Author

**Jayesh Jadhav**  
📧 jayesh.22320128@viit.ac.in
