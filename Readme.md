# Retrospective Management Service

### Tools and Libraries Used:

* Spring Boot: For building the application and handling RESTful endpoints.
* Project Lombok: For reducing boilerplate code by generating getters, setters, and constructors.

### How to start and use the app?
The following guides illustrate how to use some features concretely:

1) Clone the Repository:
git clone https://github.com/vijeth123/scrum.git


2) Build the Project:
cd spring-boot-retrospective-management
mvn clean install

3) Run the Application:
java -jar target/spring-boot-retrospective-management.jar

4) Access the API Endpoints:
    * Create a new retrospective:
   POST http://localhost:8080/retrospectives
   Example request body:   
   {
      "name": "Retrospective 1",
      "summary": "Post release retrospective",
      "date": "2022-07-27",
      "participants": [
      "Viktor",
      "Gareth",
      "Mike"
      ]
   }


    * Add feedback items to a retrospective:
   POST http://localhost:8080/retrospectives/{retrospectiveName}/feedback
   Example request URL: http://localhost:8080/retrospectives/Retrospective 1/feedback
   Example request body:
   {
      "name": "Gareth",
      "body": "Sprint objective met",
      "type": "POSITIVE"
   }


    * Update feedback items: 
   PUT http://localhost:8080/retrospectives/{retrospectiveName}/feedback/{feedbackItemName}
   Example request URL: http://localhost:8080/retrospectives/Retrospective 1/feedback/Gareth
   Example request body:
   {
   "name": "Gareth",
   "body": "Sprint objective met, including the bug fixes",
   "type": "PRAISE"
   }

    * Search retrospectives by date:
   GET http://localhost:8080/retrospectives/search?date=YYYY-MM-DD
   
    * Pagination:
   GET http://localhost:8080/retrospectives/all?currentPage={currentPage}&pageSize={pageSize}