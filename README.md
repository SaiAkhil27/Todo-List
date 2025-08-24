# ✅ To-Do List API with Spring Boot

This is a simple **To-Do List REST API** built using **Spring Boot**, **DTO pattern**, and **ModelMapper**. It supports full CRUD operations and follows best practices like controller-service-repository separation and clean API design.

---

## 🧰 Technologies Used

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL Database
- ModelMapper
- Lombok
- Maven

---

## 📁 Project Structure

com.example.todo
- controller # REST APIs
- dto # TodoDTO class
- exception # Custom exception handling
- model # JPA Entity (Todo)
- repository # JPA Repository
- service # Business logic
- TodoApplication # Main Spring Boot class


---

## 🚀 How to Run

### ✅ 1. Clone the Repository

```bash
git clone https://github.com/your-username/todo-app.git
cd todo-app
```

### ✅ Application.properties
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/tododb
spring.datasource.username=root
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
server.port=8080

```

### ✅ 2. Configure MySQL in application.properties

```bash
./mvnw clean install
```

### ✅ 3. Build the Project
```bash
./mvnw spring-boot:run
```

### ✅ Sample Request (POST)

```bash
POST /api/todos
{
  "title": "Buy groceries",
  "description": "Milk, Eggs, Bread",
  "completed": false
}
```

### 🧠 Concepts Demonstrated
- DTO pattern to separate entity from request/response
- ModelMapper for converting between Entity and DTO
- Exception handling with custom ResourceNotFoundException
- RESTful API design principles
- CRUD operations using Spring Data JPA and MySQL







