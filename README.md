# 🎓 Campus Events Hub – Java Swing Management System

A GUI-based Campus Event Management System built using Java Swing and Object-Oriented Programming (OOP) concepts.

This application allows users to manage different types of campus events, clubs, committees, and participant registrations in an interactive desktop application.

------------------------------------------------------------
📌 FEATURES
------------------------------------------------------------

📚 Hackathons  
🎉 Fests  
🎤 Seminars  
⚽ Sports Events  
🏛️ Club Events  

Functionalities:
- Add Events
- View Events (Category-wise & All)
- Update Events
- Delete Events
- Register Participants
- Manage Clubs
- Add Committee Members
- View Overall Statistics
- Exit System

------------------------------------------------------------
🏗️ OOP CONCEPTS USED
------------------------------------------------------------

- Abstraction (abstract class Event)
- Interface (Registrable)
- Inheritance (Hackathon, Fest, Seminar, SportsEvent, ClubEvent)
- Polymorphism
- Encapsulation
- Comparable Interface
- Inner Class (EventStats)
- Generics
- Java Collections Framework:
  - ArrayList
  - Vector
  - LinkedList

------------------------------------------------------------
🖥️ USER INTERFACE
------------------------------------------------------------

Built using Java Swing components:
- JFrame
- JPanel
- JButton
- JTextArea
- JOptionPane
- JScrollPane

Layouts Used:
- BorderLayout
- GridLayout

Includes hover effects, styled buttons, and clean layout.

------------------------------------------------------------
📊 STATISTICS PROVIDED
------------------------------------------------------------

- Total Events
- Total Registrations
- Total Capacity
- Overall Occupancy Percentage

------------------------------------------------------------
🧩 PROJECT STRUCTURE
------------------------------------------------------------

CampusEventHubApp.java

Contains:
- interface Registrable
- abstract class Event
- Hackathon
- Fest
- Seminar
- SportsEvent
- ClubEvent
- Committee
- Club
- EventManager
- CampusEventHubApp (Main Class)

------------------------------------------------------------
🚀 HOW TO RUN
------------------------------------------------------------

Requirements:
- Java JDK 8 or above

Compile:
javac CampusEventHubApp.java

Run:
java CampusEventHubApp

------------------------------------------------------------
🗃️ PRELOADED SAMPLE DATA
------------------------------------------------------------

- AI Challenge (Hackathon)
- BITS Tech Fest (Fest)
- Cloud Computing Seminar
- RECHARGE (Sports Event)
- Google Developer Student Club

------------------------------------------------------------
📈 FUTURE IMPROVEMENTS
------------------------------------------------------------

- File handling (Save/Load)
- Database integration (MySQL)
- Login authentication
- Date validation
- Email notifications
- JavaFX UI upgrade

------------------------------------------------------------
👨‍💻 AUTHOR
------------------------------------------------------------

Developed as a Campus Event Management System project using Java Swing and OOP principles.

------------------------------------------------------------
📜 LICENSE
------------------------------------------------------------

This project is for educational purposes.
