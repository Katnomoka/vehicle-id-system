A secure, role-based desktop application for managing vehicle records, service history, insurance policies, police reports, and customer queries. Built with JavaFX, PostgreSQL, and a strict MVC architecture with comprehensive audit logging and session management.

Role-Based Access Control
5 distinct user roles with strict module visibility & permission enforcement

Key Features
Real-Time Dashboard
tatistics cards, paginated tables, search filtering, and live session tracking
Professional UI/UX
DropShadow effects, smooth fade/scale animations, responsive layouts, and modern card design
PostgreSQL Integration
JDBC DAO layer with connection pooling, foreign key constraints, and audit trails
Modular Architecture
Separate panels for Workshop, Customer, Insurance, Police, and Admin modules
Search & Pagination
Real-time filtering across 10+ fields with smooth page transitions
Audit Logging
Every login/logout/action is recorded with timestamps and IP tracking
Data Export
CSV-ready export functionality for reports and records

Tech Stack
Language :Java 24
UI Framework: JavaFX 21 (FXML + CSS)
Database: PostgreSQL 18
Build Tool: Maven
Architecture: MVC (Model-View-Controller)
IDE: IntelliJ IDEA
Version Control: Git + GitHub

vehicle-id-system/
├── src/
│   ├── main/
│   │   ├── java/com/vehicle/identification/vehicleidsystem/
│   │   │   ├── controller/     # FXML Controllers & UI Logic
│   │   │   ├── dao/            # PostgreSQL Data Access Objects
│   │   │   ├── model/          # Entity Classes (BaseEntity inheritance)
│   │   │   ├── util/           # DBConnection, Validators, Exporters
│   │   │   └── Main.java       # Application Entry Point
│   │   └── resources/
│   │       ├── fxml/           # JavaFX Layout Files
│   │       ├── css/            # Styling & Themes
│   │       └── db.properties   # Database Configuration
│   └── test/                   # Unit Tests (Optional)
├── .gitignore
├── pom.xml                     # Maven Dependencies
└── README.md                   # This File

Role-Based Access Control (RBAC)

Role  
Visible Modules
Add/edit service history, view vehicle status


Admin
Service Records, Dashboard  
View own vehicle status, submit queries

Workshop
Service Records, Dashboard
Add/edit service history, view vehicle statusd

Customer
Customer Queries, Dashboard
View own vehicle status, submit queries

Insurance     
Insurance Policies, Dashboard
Track policies, claims, coverage status

Police
Police Reports, Violations
File reports, track violations, verify records

Security Note: Unauthorized modules are completely hidden from the navigation menu, not just disabled.

 Database Schema

 Users
Authentication & role management

Vehicle
Core vehicle registration data

ServiceRecord
Workshop maintenance & repair logs

InsurancePolicy
Coverage tracking & claim history

PoliceReport
Accident/theft documentation

Violation
Traffic infractions & fine tracking

CustomerQuery
Owner support tickets & responses

UserAccessLog
Audit trail for compliance

Clone & Configure
git clone https://github.com/Katnomoka/vehicle-id-system.git
cd vehicle-id-system

 Database Setup
Created a database named 15past.
Run the initialization script in src/main/resources/schema.sql (or executed the CREATE TABLE statements in pgAdmin)

used this fro connecion with the database
db.driver=org.postgresql.Driver
db.url=jdbc:postgresql://localhost:5432/your_database_name
db.username=your_postgres_user
db.password=your_password

Build & Run

mvn clean compile
mvn javafx:run

Default Test Accounts
admin
admin123
admin

workshop1
work123
Workshop

customer1
cust123
Customer

UI & Animations

DropShadow Effects: Context-aware colored shadows under interactive elements
Hover Animations: Scale-up + brightness transitions on buttons/cards
Click Feedback: Press-down micro-interactions for tactile UX
Page Transitions: Sequential fade-in + slide-up on dashboard load
Responsive Layouts: GridPane + VBox/HBox combos adapt to window resizing

Author & License
Developer: [Mots'lise Mafa, Lemohang Nomoka, Falla Tlaitlai]
Course/Project: OOP2 / Vehicle Identification System
License: MIT
Repository: https://github.com/Katnomoka/vehicle-id-system
