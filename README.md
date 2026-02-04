![Login](src/main/login_screen.png) ![Login](src/main/sales_home_screen.jpg) ![Login](src/main/admin_home_screen.jpg)

# Client Schedule Management System
*A JavaFX Application for Managing Appointments, Customers, and Regions*

## Overview
This application is a comprehensive scheduling system designed for sales teams. It allows administrators and sales personnel to manage client appointments, track customer records, and view reports based on geographical regions.

**Key Features:**
* **Role-Based Login:** Separate access for Administrators and Salespeople.
* **Appointment Management:** Create, update, and delete appointments with conflict detection.
* **Customer Records:** Maintain detailed client information including location data.
* **Reporting:** Generate reports for appointment types, schedules per user, and customer distribution.
* **Localization:** Supports automatic language translation (English/French) based on system settings.

## 🛠️ How to Run Locally
This project was originally designed for a cloud-based AWS database but has been updated to run completely offline for portfolio demonstration purposes.

### Prerequisites
1.  **Java JDK 17** or higher.
2.  **MySQL Server 8.0** (Community Edition).
3.  **IntelliJ IDEA** (Recommended IDE).

### Step 1: Database Setup
1.  Install **MySQL Server** and **MySQL Workbench**.
2.  During installation, set the **Root Password** to `root`.
    * *Note: If you use a different password, you must update the `password` variable in `src/database/JDBC.java`.*
3.  Open MySQL Workbench.
4.  Open the file `database_setup.sql` located in the root of this project.
5.  Run the entire script (Lightning Bolt icon) to create the database and populate it with sample data (Regions, States, Salespeople, Clients and Appointments).

### Step 2: Project Configuration
1.  Open the project in **IntelliJ IDEA**.
2.  Allow the IDE to import dependencies from the `pom.xml` file (Maven).
    * *This automatically downloads JavaFX and the MySQL Connector.*
3.  Ensure your Project SDK is set to **Java 17** (File > Project Structure > Project).

### Step 3: Launch the Application
1.  Navigate to `src/main/Launcher.java`.
2.  Click the **Green Play Button** (Run).
    * *Do not run `Main.java` directly as it may cause JavaFX runtime errors.*

### 🔑 Login Credentials
**Administrator Access:**
* **Username:** `admin`
* **Password:** `Passw0rd!`

**Salesperson Access (Northeast Region):**
* **Username:** `sales_ne`
* **Password:** `12345`

## Technologies Used
* **Java 17**
* **JavaFX** (UI Framework)
* **MySQL** (Database)
* **Maven** (Dependency Management)
