-- 1. RESET AND CREATE DATABASE
DROP DATABASE IF EXISTS client_schedule;
CREATE DATABASE client_schedule;
USE client_schedule;

-- 2. CREATE TABLES

CREATE TABLE regions (
                         Region_ID INT PRIMARY KEY,
                         Region_Name VARCHAR(50)
);

CREATE TABLE states (
                        State_ID INT PRIMARY KEY AUTO_INCREMENT,
                        State_Name VARCHAR(50),
                        State_Abbreviation VARCHAR(2),
                        Region_ID INT,
                        FOREIGN KEY (Region_ID) REFERENCES regions(Region_ID)
);

CREATE TABLE users (
                       User_ID INT PRIMARY KEY AUTO_INCREMENT,
                       User_Name VARCHAR(50) UNIQUE,
                       Password VARCHAR(50),
                       Admin_Key INT -- 0 for Salesperson, 1 for Admin
);

CREATE TABLE administrators (
                                Administrator_ID INT PRIMARY KEY AUTO_INCREMENT,
                                Administrator_First_Name VARCHAR(50),
                                Administrator_Last_Name VARCHAR(50),
                                Email VARCHAR(100),
                                User_ID INT,
                                FOREIGN KEY (User_ID) REFERENCES users(User_ID)
);

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

-- 3. INSERT DATA

-- Regions (IDs hardcoded in Java)
INSERT INTO regions (Region_ID, Region_Name) VALUES
                                                 (1001, 'Northeast'),
                                                 (1002, 'Southeast'),
                                                 (1003, 'Mid-West'),
                                                 (1004, 'Texas+'),
                                                 (1005, 'California+'),
                                                 (1006, 'Northwest');

-- All 50 States Mapped to Regions
INSERT INTO states (State_Name, State_Abbreviation, Region_ID) VALUES
-- Northeast (1001)
('Maine', 'ME', 1001), ('New Hampshire', 'NH', 1001), ('Vermont', 'VT', 1001),
('Massachusetts', 'MA', 1001), ('Rhode Island', 'RI', 1001), ('Connecticut', 'CT', 1001),
('New York', 'NY', 1001), ('New Jersey', 'NJ', 1001), ('Pennsylvania', 'PA', 1001),
('Delaware', 'DE', 1001), ('Maryland', 'MD', 1001),
-- Southeast (1002)
('Virginia', 'VA', 1002), ('West Virginia', 'WV', 1002), ('North Carolina', 'NC', 1002),
('South Carolina', 'SC', 1002), ('Georgia', 'GA', 1002), ('Florida', 'FL', 1002),
('Alabama', 'AL', 1002), ('Mississippi', 'MS', 1002), ('Tennessee', 'TN', 1002),
('Kentucky', 'KY', 1002), ('Arkansas', 'AR', 1002), ('Louisiana', 'LA', 1002),
-- Mid-West (1003)
('Ohio', 'OH', 1003), ('Indiana', 'IN', 1003), ('Illinois', 'IL', 1003),
('Michigan', 'MI', 1003), ('Wisconsin', 'WI', 1003), ('Minnesota', 'MN', 1003),
('Iowa', 'IA', 1003), ('Missouri', 'MO', 1003), ('North Dakota', 'ND', 1003),
('South Dakota', 'SD', 1003), ('Nebraska', 'NE', 1003), ('Kansas', 'KS', 1003),
-- Texas+ (Southwest) (1004)
('Texas', 'TX', 1004), ('Oklahoma', 'OK', 1004), ('New Mexico', 'NM', 1004),
('Arizona', 'AZ', 1004),
-- California+ (West) (1005)
('California', 'CA', 1005), ('Nevada', 'NV', 1005), ('Utah', 'UT', 1005),
('Colorado', 'CO', 1005), ('Hawaii', 'HI', 1005),
-- Northwest (1006)
('Washington', 'WA', 1006), ('Oregon', 'OR', 1006), ('Idaho', 'ID', 1006),
('Montana', 'MT', 1006), ('Wyoming', 'WY', 1006), ('Alaska', 'AK', 1006);

-- Users (1 Admin, 5 Salespeople)
INSERT INTO users (User_Name, Password, Admin_Key) VALUES
                                                       ('admin', 'Passw0rd!', 1),
                                                       ('sales_ne', '12345', 0),
                                                       ('sales_sw', '12345', 0),
                                                       ('sales_cali', '12345', 0),
                                                       ('sales_mw', '12345', 0),
                                                       ('sales_nw', '12345', 0);

-- Administrator Profile
INSERT INTO administrators (Administrator_First_Name, Administrator_Last_Name, Email, User_ID)
VALUES ('Super', 'Admin', 'admin@company.com', 1);

-- Salesperson Profiles
INSERT INTO salespersons (Salesperson_First_Name, Salesperson_Last_Name, Salesperson_Email, User_ID, Region_ID) VALUES
                                                                                                                    ('Dwight', 'Schrute', 'dwight@paper.com', 2, 1001), -- Northeast
                                                                                                                    ('Jim', 'Halpert', 'jim@paper.com', 3, 1004),      -- Texas+
                                                                                                                    ('Michael', 'Scott', 'boss@paper.com', 4, 1005),    -- Cali+
                                                                                                                    ('Phyllis', 'Vance', 'phyllis@paper.com', 5, 1003), -- MidWest
                                                                                                                    ('Stanley', 'Hudson', 'stanley@paper.com', 6, 1006); -- Northwest

-- Customers (Linked to Regions & Salespeople)
INSERT INTO customers (Customer_First_Name, Customer_Last_Name, Address, Zip_Code, Phone, Email, Salesperson_ID, State_ID, Region_ID) VALUES
-- Northeast Customers (Dwight)
('Alice', 'Smith', '123 Maple St', '10001', '212-555-0100', 'alice@gmail.com', 1, 7, 1001), -- NY
('Bob', 'Jones', '456 Oak Ave', '02108', '617-555-0200', 'bob@yahoo.com', 1, 4, 1001),   -- MA
('Charlie', 'Brown', '789 Pine Rd', '19103', '215-555-0300', 'charlie@comcast.net', 1, 9, 1001), -- PA

-- Texas+ Customers (Jim)
('David', 'Miller', '101 Cowboy Way', '75001', '214-555-0400', 'david@tesla.com', 2, 35, 1004), -- TX
('Eva', 'Longoria', '202 Desert Ln', '85001', '602-555-0500', 'eva@hollywood.com', 2, 38, 1004), -- AZ

-- California+ Customers (Michael)
('Frank', 'Sinatra', '303 Sunset Blvd', '90001', '323-555-0600', 'frank@music.com', 3, 39, 1005), -- CA
('Grace', 'Hopper', '404 Silicon Dr', '94025', '650-555-0700', 'grace@tech.com', 3, 39, 1005),  -- CA

-- MidWest Customers (Phyllis)
('Harry', 'Potter', '505 Cornfield Cir', '43085', '614-555-0800', 'harry@magic.com', 4, 24, 1003), -- OH
('Ivy', 'League', '606 Lake View', '60601', '312-555-0900', 'ivy@chicago.com', 4, 26, 1003),   -- IL

-- Northwest Customers (Stanley)
('Jack', 'Black', '707 Rain St', '98101', '206-555-1000', 'jack@grunge.com', 5, 44, 1006), -- WA
('Kelly', 'Clarkson', '808 Potato Path', '83702', '208-555-1100', 'kelly@idol.com', 5, 46, 1006); -- ID

-- Appointments
INSERT INTO appointments (Title, Type, Start, End, Customer_ID, Salesperson_ID, State_ID, Region_ID) VALUES
-- UPCOMING (March - May 2026)
('Sales Pitch', 'In-Person', '2026-03-10 09:00:00', '2026-03-10 10:00:00', 1, 1, 7, 1001),
('Contract Review', 'Virtual', '2026-03-12 14:00:00', '2026-03-12 15:00:00', 2, 1, 4, 1001),
('Quarterly Review', 'In-Person', '2026-03-15 11:00:00', '2026-03-15 12:00:00', 3, 1, 9, 1001),
('New Client Meeting', 'In-Person', '2026-04-01 10:00:00', '2026-04-01 11:30:00', 4, 2, 35, 1004),
('Follow Up', 'Phone', '2026-04-05 13:00:00', '2026-04-05 13:30:00', 5, 2, 38, 1004),
('Lunch Meeting', 'In-Person', '2026-03-20 12:00:00', '2026-03-20 13:30:00', 6, 3, 39, 1005),
('Tech Demo', 'Virtual', '2026-03-22 15:00:00', '2026-03-22 16:00:00', 7, 3, 39, 1005),
('Coffee Chat', 'In-Person', '2026-03-05 08:30:00', '2026-03-05 09:30:00', 8, 4, 24, 1003),
('Proposal Delivery', 'In-Person', '2026-03-08 16:00:00', '2026-03-08 17:00:00', 9, 4, 26, 1003),
('Site Visit', 'In-Person', '2026-05-12 10:00:00', '2026-05-12 12:00:00', 10, 5, 44, 1006),
('Closing Deal', 'Virtual', '2026-05-15 14:00:00', '2026-05-15 15:00:00', 11, 5, 46, 1006),

-- COMPLETED (Historical Data: 2025 - Jan 2026)
('Intro Call', 'Phone', '2026-01-10 10:00:00', '2026-01-10 10:30:00', 1, 1, 7, 1001),
('Budget Review', 'Virtual', '2025-12-15 14:00:00', '2025-12-15 15:00:00', 1, 1, 7, 1001),
('Initial Consult', 'In-Person', '2025-11-05 09:00:00', '2025-11-05 10:00:00', 4, 2, 35, 1004),
('Holiday Check-in', 'Phone', '2025-12-20 11:00:00', '2025-12-20 11:15:00', 5, 2, 38, 1004),
('Strategy Session', 'Virtual', '2026-01-22 15:30:00', '2026-01-22 16:30:00', 6, 3, 39, 1005),
('Demo Prep', 'In-Person', '2026-01-25 10:00:00', '2026-01-25 12:00:00', 7, 3, 39, 1005),
('Year End Review', 'Virtual', '2025-12-28 13:00:00', '2025-12-28 14:00:00', 8, 4, 24, 1003),
('Kickoff Meeting', 'In-Person', '2026-01-05 09:00:00', '2026-01-05 10:00:00', 10, 5, 44, 1006);