# Student Result Management System

Java Swing + JDBC + MySQL desktop application demonstrating OOP, layered architecture, DAO pattern, and CRUD operations.

## Requirements

- Java 17
- Maven
- MySQL 8+

## Setup

1. Create the database using the SQL script:

```sql
src/main/resources/schema.sql
```

2. Update database credentials:

```
src/main/resources/db.properties
```

3. Build and run:

```bash
mvn clean package
java -cp target/student-result-management-system-1.0.0.jar com.srms.main.Main
```

## Default User

Insert at least one user into `users` table (password is plain text for this semester project).

## Project Structure

- `model` - Entity classes
- `dao` / `dao.impl` - DAO interfaces and JDBC implementations
- `service` - Business logic and validation
- `ui` - Swing UI
- `database` - JDBC connection
- `utils` - Helper utilities
- `exceptions` - Custom exceptions
