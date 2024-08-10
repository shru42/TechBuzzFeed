# TechBuzzFeed
Welcome to TechBuzzFeed! This is a dynamic Q&A platform focusing on tech gossip. Users can post questions, provide answers, and interact with other tech enthusiasts.

Features
User Profiles: Each user has a profile that includes their questions, answers, and activity history. Profiles can be viewed, edited, or deleted.
Tag Management: Users can add and manage tags. Tags are stored as IDs in the backend for efficient querying.
Voting System: Users can upvote or downvote answers. The most popular answers are highlighted.
Pagination and Sorting: Easily navigate through questions and answers with pagination and sorting features.
gRPC Integration: Utilizes gRPC for efficient communication and handling of request and response headers.
Security: Includes robust security features to protect user data.
JWT Authentication: Secures API endpoints with JSON Web Token (JWT) authentication.
Technologies Used
Backend: Spring Boot, Java
Database: MySQL
Frontend: HTML, CSS, JavaScript
gRPC: For efficient inter-service communication
ModelMapper: For converting between DTOs and entities
Installation
Clone the Repository

bash
Copy code
git clone https://github.com/your-username/techbuzzfeed.git
cd techbuzzfeed
Set Up the Database

Ensure you have MySQL installed.
Create a new database and import the schema from db/schema.sql.
Configure Application Properties

Update src/main/resources/application.properties with your database and other configurations.
Run the Application

bash
Copy code
./mvnw spring-boot:run
Usage
Access the Application: Open http://localhost:8080 in your web browser.
API Endpoints: Refer to src/main/resources/api-docs for detailed API documentation.
Security: JWT tokens are required for accessing protected endpoints. Include the token in the Authorization header of your requests.
