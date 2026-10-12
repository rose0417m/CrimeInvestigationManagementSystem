# Crime Investigation Management System

A Java-based desktop application designed to manage crime investigation records, detectives, suspects, witnesses, evidence, and case-related information in a centralized MySQL database.

## Project Overview

The Crime Investigation Management System (CIMS) helps authorized users organize investigation records and manage the information associated with individual cases. It provides a graphical user interface for maintaining records, tracking case status, and connecting suspects and witnesses to specific cases.

## Features

- **Login and Authentication** — Secure login with password hashing.
- **User Management** — Admin-only functionality for creating investigator accounts.
- **Case Management** — Add, view, and update cases, including case numbers, descriptions, status, and assigned detectives.
- **Detective Management** — Maintain detective records and specializations.
- **Suspect Management** — Store suspect details such as name, age, gender, address, and phone number.
- **Witness Management** — Maintain witness contact information and statements.
- **Case–Suspect Linking** — Associate existing suspects with investigation cases.
- **Case–Witness Linking** — Associate existing witnesses with investigation cases.
- **Evidence Management** — Maintain evidence records.
- **Search** — Search investigation records.
- **Reports** — View case records and related investigation information.

## Technologies Used

- **Programming Language:** Java
- **GUI:** Java Swing
- **Database:** MySQL
- **Database Connectivity:** JDBC
- **Build Tool:** Apache Maven
- **Password Hashing:** PBKDF2
- **Development Environment:** IntelliJ IDEA
- **Version Control:** Git and GitHub

## Project Structure

```text
CrimeInvestigationManagementSystem/
├── src/
│   └── main/
│       └── java/
│           ├── dao/
│           │   ├── CaseDAO.java
│           │   ├── CaseLinkDAO.java
│           │   ├── DetectiveDAO.java
│           │   ├── SuspectDAO.java
│           │   ├── WitnessDAO.java
│           │   └── UserDAO.java
│           ├── database/
│           │   └── DatabaseConnection.java
│           ├── gui/
│           │   ├── LoginFrame.java
│           │   ├── DashboardFrame.java
│           │   ├── CaseFrame.java
│           │   ├── UserManagementFrame.java
│           │   └── ...
│           ├── model/
│           │   └── ...
│           └── util/
│               └── PasswordUtil.java
├── pom.xml
└── README.md
```

*The structure above highlights the main components; additional classes and resources may be present in the repository.*

## Prerequisites

Install the following before running the application:

- Java Development Kit (JDK)
- IntelliJ IDEA or another Java IDE
- MySQL Server
- MySQL Workbench (optional, for managing the database)
- Git
- Apache Maven, or use the Maven support available in IntelliJ IDEA

## Getting Started

### 1. Clone the repository

Open a terminal and run:

```bash
git clone https://github.com/rose0417m/CrimeInvestigationManagementSystem.git
```

Open the downloaded project in IntelliJ IDEA and allow Maven to load its dependencies.

### 2. Configure the database

Start your MySQL Server and create the project database:

```sql
CREATE DATABASE IF NOT EXISTS crime_investigation_db;
```

Ensure that the database schema and tables required by the project's DAO classes have been created. The application expects the database structure to match the SQL queries used by the source code.

The case-linking functionality uses these additional tables:

```sql
USE crime_investigation_db;

CREATE TABLE IF NOT EXISTS case_suspects (
    case_id INT NOT NULL,
    suspect_id INT NOT NULL,
    PRIMARY KEY (case_id, suspect_id)
);

CREATE TABLE IF NOT EXISTS case_witnesses (
    case_id INT NOT NULL,
    witness_id INT NOT NULL,
    PRIMARY KEY (case_id, witness_id)
);
```

These tables associate existing suspect and witness records with cases.

### 3. Configure the database connection

Open:

```text
src/main/java/database/DatabaseConnection.java
```

Configure the MySQL connection URL, username, and password for your local MySQL Server.

Keep database credentials private. Do not commit personal passwords or other secrets to GitHub.

### 4. Run the application

In IntelliJ IDEA:

1. Open the project and wait for Maven dependencies to finish loading.
2. Locate `gui.LoginFrame`.
3. Run its `main` method, or use the project's existing run configuration.
4. Log in using an administrator account configured in your database.

The dashboard provides access to the available management modules according to the application's role checks.

### 5. Build the project

From the project root, run:

```bash
mvn clean package
```

If the configured Maven build produces the runnable JAR, it can be launched with:

```bash
java -jar target/CrimeInvestigationManagementSystem-1.0-SNAPSHOT.jar
```

If your Maven configuration uses a different JAR filename, use the generated filename in the `target` directory.

## Database Relationships

The application stores investigation records in MySQL. The case management functionality supports relationships between cases and their assigned detectives.

The additional linking tables support these relationships:

- **Case–Suspect:** Associates a case with one or more suspects.
- **Case–Witness:** Associates a case with one or more witnesses.

A suspect or witness can be linked to more than one case. These links are stored in the database and remain available after the application is restarted.

## Security

The application uses password hashing for authentication and restricts account creation to administrators.

This project is intended for educational and demonstration purposes. Before any real-world deployment, additional security hardening, access-control review, validation, and database security measures would be required.

## Team and Contribution

This project was developed as an academic software project.

To contribute, fork the repository or create a branch, make your changes, and submit a pull request.

## Repository

GitHub: https://github.com/rose0417m/CrimeInvestigationManagementSystem
