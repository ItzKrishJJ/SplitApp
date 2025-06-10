🌐 Live Deployment
🔗 https://splitapp-production.up.railway.app

# 💸 SplitApp - Expense Splitter

A full-stack backend service built using **Spring Boot** and **PostgreSQL** for splitting expenses among people. Designed for personal use or small groups to settle up efficiently.

---

## 🚀 Features

- Add people and expenses
- Split expenses equally or by exact amount/percent
- Calculate balances per person
- View optimized settlements
- RESTful APIs with clear DTOs and validations

---

## 🔧 Tech Stack

- **Backend:** Java 17, Spring Boot 3
- **Database:** PostgreSQL
- **ORM:** JPA + Hibernate
- **Build Tool:** Maven
- **Deployment:** Railway (free-tier)
- **Testing:** Postman Collection

---

## 🏁 Getting Started

### ✅ Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL (locally or via Railway)
- Git

---
 ⚙️ Local Setup Instructions

# Clone the repository
git clone https://github.com/YOUR_USERNAME/SplitApp.git
cd SplitApp

# Set up environment variables
cp .env.example .env   # Or use application.properties manually

# Configure PostgreSQL credentials in `src/main/resources/application.properties`
# Or use environment variables for datasource URL, username, and password

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
App will start at:
👉 http://localhost:8080

🗂️ API Endpoints
Full Postman Collection included:
📂 Expense Splitter APIs.postman_collection.json

Key Endpoints:

Method	Endpoint	Description
GET	/people	List all persons
POST	/people	Add a person
POST	/expenses	Add new expense
GET	/balances	Get balances
GET	/settlements	Get optimized settlements

🗃️ Database Schema
Tables created via JPA:

person

expense

expense_share

🔍 Entity Overview:
java
Copy
Edit
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
If using PostgreSQL manually, create schema with:

sql
Copy
Edit
CREATE TABLE person (...);
CREATE TABLE expense (...);
CREATE TABLE expense_share (...);
Or export from Hibernate logs after first run.

🛠️ Configuration
Your main application.properties should look like:

properties
Copy
Edit
spring.datasource.url=jdbc:postgresql://localhost:5432/splitapp
spring.datasource.username=your_pg_user
spring.datasource.password=your_pg_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
For Railway deployment, use environment variables instead.


✅ TODOs / Future Enhancements
 Add user authentication (JWT)

 Export settlement as PDF or CSV

 React frontend for UI

 Flyway DB migration support

🧑‍💻 Author
Made with ❤️ by Jayesh Jadhav
