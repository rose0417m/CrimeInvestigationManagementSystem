# Crime Investigation Management System

A Java-based desktop application for managing crime investigation records such as cases, detectives, suspects, witnesses, and evidence.

## Technologies Used

- Java
- Java Swing
- MySQL
- JDBC
- Maven
- IntelliJ IDEA
- Git & GitHub

## Main Features

- User login and authentication
- Case management
- Detective management
- Suspect management
- Witness management
- Evidence management
- Search investigation records
- Generate investigation reports
- MySQL database integration

## Project Structure

```text
src/main/java/
├── dao/
│   ├── CaseDAO.java
│   ├── DetectiveDAO.java
│   ├── EvidenceDAO.java
│   ├── SuspectDAO.java
│   ├── UserDAO.java
│   └── WitnessDAO.java
│
├── database/
│   └── DatabaseConnection.java
│
├── gui/
│   ├── LoginFrame.java
│   ├── DashboardFrame.java
│   ├── CaseFrame.java
│   ├── DetectiveFrame.java
│   ├── SuspectFrame.java
│   ├── WitnessFrame.java
│   ├── EvidenceFrame.java
│   ├── SearchFrame.java
│   └── ReportFrame.java
│
├── model/
│   ├── Person.java
│   ├── User.java
│   ├── Detective.java
│   ├── Suspect.java
│   ├── Witness.java
│   ├── Case.java
│   └── Evidence.java
│
└── Main.java


Database

The project uses MySQL with the database:

crime_investigation_db

The database setup script is available at:

database/database_setup.sql
OOP Concepts Demonstrated

The project demonstrates the following Java OOP concepts:

Encapsulation through private attributes and getters/setters
Abstraction using the Person abstract class
Inheritance using Detective, Suspect, and Witness
Polymorphism through method overriding
Constructors
Exception handling
DAO-based separation of database operations
How to Run
1. Set up MySQL

Run:

database/database_setup.sql

in MySQL Workbench.

2. Configure database connection

Create your local database connection configuration using the provided example file.

Do not commit passwords or other credentials to GitHub.

3. Build the project

Using the Maven wrapper:

.\mvnw.cmd clean compile
4. Run the application

Run:

src/main/java/Main.java

The application opens with the login screen.

Default Test Account
Username: admin
Password: 1234
Application Flow
Login
  ↓
Dashboard
  ↓
Case Management
Detective Management
Suspect Management
Witness Management
Evidence Management
Search
Reports

Team Project

This project is developed as a Java OOP group project using Java Swing, MySQL and JDBC.
