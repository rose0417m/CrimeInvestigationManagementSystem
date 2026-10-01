CREATE DATABASE IF NOT EXISTS crime_investigation_db;

USE crime_investigation_db;

-- Users table
CREATE TABLE IF NOT EXISTS users (
                                     user_id INT PRIMARY KEY AUTO_INCREMENT,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
    );

-- Detectives table
CREATE TABLE IF NOT EXISTS detectives (
                                          detective_id INT PRIMARY KEY AUTO_INCREMENT,
                                          name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    specialization VARCHAR(100)
    );

-- Suspects table
CREATE TABLE IF NOT EXISTS suspects (
                                        suspect_id INT PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(100) NOT NULL,
    age INT,
    gender VARCHAR(20),
    address VARCHAR(255),
    phone VARCHAR(20)
    );

-- Witnesses table
CREATE TABLE IF NOT EXISTS witnesses (
                                         witness_id INT PRIMARY KEY AUTO_INCREMENT,
                                         name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    statement TEXT
    );

-- Cases table
CREATE TABLE IF NOT EXISTS cases (
                                     case_id INT PRIMARY KEY AUTO_INCREMENT,
                                     case_number VARCHAR(50) NOT NULL UNIQUE,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    status VARCHAR(30) DEFAULT 'Open',
    date_created DATE,
    detective_id INT,
    FOREIGN KEY (detective_id)
    REFERENCES detectives(detective_id)
    );

-- Evidence table
CREATE TABLE IF NOT EXISTS evidence (
                                        evidence_id INT PRIMARY KEY AUTO_INCREMENT,
                                        case_id INT NOT NULL,
                                        evidence_type VARCHAR(100),
    description TEXT,
    location_found VARCHAR(255),
    date_collected DATE,
    FOREIGN KEY (case_id)
    REFERENCES cases(case_id)
    );

-- Default login account
INSERT IGNORE INTO users (username, password, role)
VALUES ('admin', '1234', 'Admin');