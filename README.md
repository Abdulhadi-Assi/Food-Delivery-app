# food delivery project 🍔🚚

A RESTful food delivery application built with Spring Boot and Spring Security. Handles user authentication, restaurant/menu management, order processing, and role-based access control.

## Technologies Used 🛠️
- **Backend**: Spring Boot
- **Security**: Spring Security, JWT Authentication
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA
- **Build Tool**: Maven

## Features ✨
- User authentication with JWT
- Role-based access control (USER, ADMIN)
- Restaurant menu browsing
- Order creation/modification
- order history tracking

## Getting Started 🚀

### Prerequisites
- Java {{17}}+
- Maven 3.8+
- PostgreSQL
- PostGIS

### Installation ⚙️

1. **Clone repo**
   ```bash
   git clone {{https://github.com/yourusername/food-delivery-app.git}}
   cd food-delivery-app

1. **Configure database**

    Edit src/main/resources/application.properties:

        spring.datasource.url=jdbc:postgresql://localhost:5432/your_db_name
        spring.datasource.username={{your_username}}
        spring.datasource.password={{your_password}}

3. **Build & run**
    ```bash
        mvn clean install
        java -jar target/food-delivery-app-0.0.1-SNAPSHOT.jar
