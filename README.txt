Author: Gregory Farrell
gfarrell2@Live.com
Version 1.1
1/29/2023

Application Name: Appointment Organizer Plus+
Purpose: This program is a GUI based desktop application with the primary purpose of maintaining salesperson users' client appointments. It interfaces with a
MySQL database hosted on AWS and allows the user to create, edit, modify and delete both clients and appointments from the database. It also supports an administrative class of user that can view all of the activity of the salesperson users.

IDE: IntelliJ IDEA 2021.2.4
JDK: 17.0.2
JavaFX: 17.0.2
Database: MySQL hosted on AWS
Driver: AWS-mysql-jdbc-1.1.3

Directions: The user must initially enter a valid username and password (admin: Passw0rd! or user1: 12345) existing within the database.
Upon authentication, the user will either be presented with the Salesperson Home Screen or Administrator Home Screen depending on their credentials.
Both types of users are able to create, edit, modify and delete appointments and Admin users are able to additionally create new users for the application.

**The AWS username and password have been removed from the JDBC.java file for security purposes but are available upon request**

