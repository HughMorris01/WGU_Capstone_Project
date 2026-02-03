![Login](src/main/login_screen.png) ![Login](src/main/sales_home_screen.jpg) ![Login](src/main/admin_home_screen.jpg)

Appointment Organizer Plus+
A robust, enterprise-ready scheduling and CRM solution designed to manage complex salesperson-client relationships. This application demonstrates high-level proficiency in multi-tier architecture, cloud-based data management, and globalization.

🌟 Key Features
Cloud-Integrated Persistence: Leverages AWS RDS (MySQL) for a centralized, globally accessible database.

Role-Based Access Control (RBAC): Distinct workflows for Administrators and Salespeople, ensuring data security and functional isolation.

Internationalization (i18n): Full support for English and French locales, including UI translation and time-zone-aware scheduling.

Business Logic Validation: Advanced conflict-checking algorithms prevent overlapping appointments for both clients and sales staff.

Automated Auditing: Maintains a detailed login_activity.txt log to track successful and failed authentication attempts for security auditing.

🏗️ Technical Architecture
The project strictly adheres to the Model-View-Controller (MVC) pattern to maintain a clean separation between UI logic and data models.

Front-End: JavaFX 17 with FXML for declarative UI design.

Back-End: Java 17 logic utilizing JDBC for optimized SQL performance.

Infrastructure: Hosted on AWS with dedicated database drivers for reliable connectivity.

🛠️ Development Stack
IDE: IntelliJ IDEA 2021.2.4

Language/GUI: JDK 17 & JavaFX 17

Database: MySQL (AWS Hosted)

Drivers: AWS-mysql-jdbc-1.1.3

🚀 Getting Started
Clone the Repo:

Bash
git clone https://github.com/HughMorris01/WGU_Capstone_Project.git
Configure Database: Update JDBC.java with your specific AWS RDS credentials.

Run: Execute the Main.java class to launch the login interface.

🧠 Technical Deep-Dive (Initial Review)
Based on the files provided, here are three high-impact "clean-up" suggestions for the code itself:

1. Extract Business Logic (The Service Layer)
In AdminCreateEditAppointmentScreenController.java, your onSubmit method is doing a lot of heavy lifting—calculating time differences, checking for conflicts, and calling the database.

Fix: Create a Service class (e.g., AppointmentService.java). Move the conflict-checking logic there. This makes your code more testable and your controller much shorter.

2. Standardize Input Validation
Currently, you use many if(field == null) blocks followed by Alert pop-ups.

Fix: Create a Validator utility class. You could pass a list of fields to a single method that returns a boolean, keeping your controllers clean.

3. Move from Procedural to DAO Pattern
Your DBAppointments.java is an abstract class with static methods. While functional, a Data Access Object (DAO) interface would be more professional and allow for easier "mocking" if you ever write unit tests.
