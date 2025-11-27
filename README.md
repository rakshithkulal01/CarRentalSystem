# Car Rental System

A full-stack web application for managing car rentals built with Spring Boot, JPA/Hibernate, and Thymeleaf. The system supports user authentication, car inventory management, and rental operations with role-based access control.

## Features

- **User Management**
  - User registration and authentication
  - Role-based access (Admin/Customer)
  - Profile management with contact details

- **Car Management**
  - Add, update, and delete cars (Admin)
  - View available cars
  - Track car availability status
  - Car details including make, model, year, and pricing

- **Rental Operations**
  - Rent available cars
  - View rental history
  - Track rental dates
  - Automatic availability updates

- **Admin Dashboard**
  - Manage car inventory
  - View all rentals
  - User management

## Tech Stack

- **Backend**: Spring Boot 3.2.5
- **Database**: SQLite with Hibernate ORM
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven
- **Java Version**: 17

## Prerequisites

- Java 17 or higher
- Maven 3.6+

## Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd CarRentalSystem
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:9092`

## Project Structure

```
src/
├── main/
│   ├── java/com/carrental/
│   │   ├── controller/       # Web controllers
│   │   ├── model/            # Entity classes
│   │   ├── repository/       # Data access layer
│   │   ├── service/          # Business logic
│   │   └── CarRentalSystemApplication.java
│   └── resources/
│       ├── templates/        # Thymeleaf templates
│       └── application.properties
```

## Database

The application uses SQLite as the database, which is automatically created as `car_rental.db` in the project root directory. The schema is managed by Hibernate with the `ddl-auto=update` strategy.

### Entities

- **User**: Stores user information including name, email, password, phone, address, and role
- **Car**: Contains car details (make, model, year, price per day, availability)
- **Rental**: Tracks rental transactions linking users and cars with rental dates

## Configuration

Key configurations in `application.properties`:

```properties
spring.datasource.url=jdbc:sqlite:car_rental.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
server.port=9092
```

## Usage

### For Customers:
1. Register a new account
2. Browse available cars
3. Rent a car by selecting from available inventory
4. View your rental history

### For Admins:
1. Log in with admin credentials
2. Access the admin dashboard
3. Manage car inventory (add/edit/delete)
4. View all rentals and users

## API Endpoints

- `/` - Home page
- `/register` - User registration
- `/login` - User login
- `/dashboard` - User dashboard
- `/customer/**` - Customer operations
- `/admin/**` - Admin operations (restricted)

## Development

To enable SQL query logging for debugging, the application is configured with:
```properties
spring.jpa.show-sql=true
```

## License

This project is available for educational purposes.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
