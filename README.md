# messaging-system backend
This repository contains the backend files for a messaging system built using Java, Spring Boot, and JPA ORM for database persistence.



Features

1. Authentication
 
The messaging system backend provides user authentication through a sign-up and login mechanism.
 Users can securely create accounts and log in to access the messaging functionalities.


2 Messaging

User Messages: Users can send messages through the system.
Agent Responses: Agents have the capability to respond to messages.



3. Workload Scheme - Assigning of Messages to Agents
   
The system dynamically assigns messages to agents based on their current workload.
 Messages are intelligently distributed to agents with less workload, ensuring efficient handling of incoming messages.

4. Canned Messages

Agents can create and store canned messages for common responses.
This feature enhances response speed, allowing agents to reply to messages using predefined and approved templates.


5. Priority Assignment for Urgent Messages

The messaging system dynamically assigns priority to messages based on their content.
 Messages deemed more urgent are prioritized, ensuring that critical issues are addressed promptly.


6. Search Functionality

The system includes a powerful search functionality that enables agents to search for messages using keywords.
This feature facilitates quick retrieval of relevant messages, enhancing overall efficiency in message management.



Additional Information

For detailed API documentation and endpoints, refer to the Swagger documentation available at http://localhost:8080/swagger-ui.html when the application is running.
Feedback and Contributions
We welcome feedback and contributions to enhance the functionality and performance of the messaging system. 
Please follow our contribution guidelines for more information.



1.Prerequisites


Before you begin, ensure you have the following installed on your local machine:
    
  java: Ensure that you have Java installed. You can download and install the latest version from Oracle's website or use OpenJDK.

  Maven: Maven is used for building and managing the project. You can download it from Maven's official website.

  Database: This project uses JPA for database persistence. Ensure that you have a relational database ( MySQL)
  installed on your machine. 
  Create a database for this project and update the application.properties file with the database connection details.



2.Getting Started

Follow these steps to set up and run the messaging system backend on your local machine:
  Clone the Repository:
      bash : git clone https://github.com/your-username/messaging-system-backend.git
  Navigate to the Project Directory:
      bash  : cd messaging-system-backend



3.Configure Database:

Update the src/main/resources/application.properties file with your database connection details:
    properties
        spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
        spring.datasource.username=your_database_username
        spring.datasource.password=your_database_password



4.Build the Project:

   bash : mvn clean install
      Run the Application:
            bash : java -jar target/messaging-system-backend.jar

  Alternatively, you can use Maven to run the application:
      bash :  mvn spring-boot:run


5. Access the Application:

 Once the application is running, you can access the messaging system backend at http://localhost:8080.


7. API Documentation

The API documentation is available at http://localhost:8080/swagger-ui.html when the application is running.
This page provides detailed information about the available API endpoints and how to interact with them.
This project is licensed under the MIT License - see the LICENSE file for details.
