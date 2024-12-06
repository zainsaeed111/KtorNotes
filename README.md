# KtorNotes - Notes API

## Project Overview
This is a simple Notes API built with **Ktor** and **Ktorm** to demonstrate basic CRUD operations on a notes database. It is connected to a **MySQL** database for data persistence.

### Features
- Add, update, delete, and retrieve notes.
- Ktor-based REST API for lightweight server-side operations.
- Ktorm ORM for easy interaction with MySQL.

## Getting Started

### Prerequisites
Ensure the following tools are installed on your system:
- [Kotlin](https://kotlinlang.org/docs/getting-started.html)
- [Gradle](https://gradle.org/install/)
- [MySQL](https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/)
- An IDE like IntelliJ IDEA or VSCode with Kotlin support.

### Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/zainsaeed111/KtorNotes.git
   cd KtorNotes



2-Configure MySQL Database: Create the necessary database and tables:

db {
  url = "jdbc:mysql://localhost:3306/notes_db"
  user = "root"
  password = "password"
}

CREATE DATABASE notes_db;
USE notes_db;

-- Create tables
CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255),
  password VARCHAR(255)
);

CREATE TABLE notes (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT,
  content TEXT,
  FOREIGN KEY (user_id) REFERENCES users(id)
);


3. Build and Run the Project: To build and run the project:
./gradlew run

4. Access the API: The server will be running locally at http://localhost:8080. You can use tools like Postman or Curl to interact with the API:

Add-Notes
->POST /AddNotes
Body: { "title": "Sample Note", "content": "This is a sample note." }

Update-Notes
PUT /UpdateNotes/{id}
Body: { "title": "Updated Title", "content": "Updated content." }

Get-Notes
->GET /GetNotes

Delete-Notes
->Delete  /DeleteNotes/{id}

##Tech Stack
Kotlin: Programming language.
Ktor: Framework for building REST APIs.
Ktorm: Lightweight ORM for Kotlin.
MySQL: Database for persisting note

Contribution
Feel free to open issues or submit PRs if you have any improvements or suggestions!

License & Copyright
This project is licensed under the MIT License.

© 2024 Zain Saeed. All rights reserved. You may use this project under the terms of the MIT License, but proper credit must be given to the original author. If using this in commercial projects, please seek written permission from the author.
