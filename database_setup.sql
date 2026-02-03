-- 1. Create and Select the Database
CREATE DATABASE IF NOT EXISTS client_schedule;
USE client_schedule;

-- 2. Create Tables (Order matters for Foreign Keys)

-- Regions Table
CREATE TABLE regions (
    Region_ID INT PRIMARY KEY,
    Region_Name VARCHAR(50)
);

-- States Table
CREATE TABLE states (
    State_ID INT PRIMARY KEY AUTO_INCREMENT,
    State_Name VARCHAR(50),
    State_Abbreviation VARCHAR(2),
    Region_ID INT,
    FOREIGN KEY (Region_ID) REFERENCES regions(Region_ID)
);

-- Users Table (Stores login credentials)
CREATE TABLE users (
    User_ID INT PRIMARY KEY AUTO_INCREMENT,
    User_Name VARCHAR(50) UNIQUE,
    Password VARCHAR(50),
    Admin_Key INT -- 0 for Salesperson, 1 for Admin
);

-- Administrators Table
CREATE TABLE administrators (
    Administrator_ID INT PRIMARY KEY AUTO_INCREMENT,
    Administrator_First_Name VARCHAR(50),
    Administrator_Last_Name VARCHAR(50),
    Email VARCHAR(100),
    User_ID INT,
    FOREIGN KEY (User_ID) REFERENCES users(User_ID)
);

-- Salespersons Table
CREATE TABLE salespersons (
    Salesperson_ID INT PRIMARY KEY AUTO_INCREMENT,
    Salesperson_First_Name VARCHAR(50),
    Salesperson_Last_Name VARCHAR(50),
    Salesperson_Email VARCHAR(100),
    User_ID INT,
    Region_ID INT,
    FOREIGN KEY (User_ID) REFERENCES users(User_ID),
    FOREIGN KEY (Region_ID) REFERENCES regions(Region_ID)
);

-- Customers Table
CREATE TABLE customers (
    Customer_ID INT PRIMARY KEY AUTO_INCREMENT,
    Customer_First_Name VARCHAR(50),
    Customer_Last_Name VARCHAR(50),
    Address VARCHAR(100),
    Zip_Code VARCHAR(20),
    Phone VARCHAR(20),
    Email VARCHAR(100),
    Salesperson_ID INT,
    State_ID INT,
    Region_ID INT,
    FOREIGN KEY (Salesperson_ID) REFERENCES salespersons(Salesperson_ID),
    FOREIGN KEY (State_ID) REFERENCES states(State_ID),
    FOREIGN KEY (Region_ID) REFERENCES regions(Region_ID)
);

-- Appointments Table
CREATE TABLE appointments (
    Appointment_ID INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(50),
    Type VARCHAR(50),
    Start DATETIME,
    End DATETIME,
    Customer_ID INT,
    Salesperson_ID INT,
    State_ID INT,
    Region_ID INT,
    FOREIGN KEY (Customer_ID) REFERENCES customers(Customer_ID),
    FOREIGN KEY (Salesperson_ID) REFERENCES salespersons(Salesperson_ID),
    FOREIGN KEY (State_ID) REFERENCES states(State_ID),
    FOREIGN KEY (Region_ID) REFERENCES regions(Region_ID)
);

-- 3. Insert Essential Data

-- Regions (IDs hardcoded in your Java app)
INSERT INTO regions (Region_ID, Region_Name) VALUES 
(1001, 'Northeast'),
(1002, 'Southeast'),
(1003, 'Mid-West'),
(1004, 'Texas+'),
(1005, 'California+'),
(1006, 'Northwest');

-- Sample States (Just a few to make the app work)
INSERT INTO states (State_Name, State_Abbreviation, Region_ID) VALUES
('New York', 'NY', 1001),
('Florida', 'FL', 1002),
('Ohio', 'OH', 1003),
('Texas', 'TX', 1004),
('California', 'CA', 1005),
('Washington', 'WA', 1006);

-- Default Admin User (Matches README instructions)
INSERT INTO users (User_Name, Password, Admin_Key) VALUES ('admin', 'Passw0rd!', 1);
INSERT INTO administrators (Administrator_First_Name, Administrator_Last_Name, Email, User_ID) 
VALUES ('Super', 'Admin', 'admin@company.com', 1);

-- Default Sales User
INSERT INTO users (User_Name, Password, Admin_Key) VALUES ('user1', '12345', 0);
INSERT INTO salespersons (Salesperson_First_Name, Salesperson_Last_Name, Salesperson_Email, User_ID, Region_ID) 
VALUES ('John', 'Doe', 'john@company.com', 2, 1001);