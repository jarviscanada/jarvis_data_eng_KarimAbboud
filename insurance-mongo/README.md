# MongoDB Spring Boot Insurance App
This application defines an API used to interface with a car insurance database that details information about people in addition to their ownership of vehicles. It defines several API calls to retrieve query information from the associated database for making business decisions. This API is built using Java through the Spring Boot Framework. It follows the standard Maven application structure. The application connects to a provided MongoDB database with connection parameters defined in the `application.properties` file.

Technologies: MongoDB, Core Java, Spring Boot, Maven, Git, GitHub

# Quick Start
Dependencies: Maven, JDK version 17 (Minimum)

Verify that your mongodb database is up and running. 

Navigate to `insurance_mongo/src/main/resources` and modify the `application.properties` file to connect to your database's IP and port number. It is set to default local mongoDB database settings by default. 

To build the application, run the following command in the `insurance_mongo` directory:

`mvn package`

To run the application, run the following command (You can skip the preceding steps on subsequent uses of the application):

`java -jar target/insurance-mongo-0.0.1-SNAPSHOT.jar ca.jrvs.insurance_api.InsuranceApiApplication`

# Implemenation

Implementation followed the standard structure of Spring Boot applications:
- Models of the relevent data were created through three seperate classes detailing the defining features of a person, car, and address. The car and address classes effectively ask as internal objects to define a person's car ownership and home address respectively. Individual instances of the Person class represent the actual data stored in our database.
- The PersonRepository.java repository was created to extend the basic MongoRepository interface in order to provide common query operations for use by the service layer. Most of the basic functionality for service layer methods was provided by the MongoRepository interface, but custom aggregations provided by this application were created in the PersonRepository interface.
- The service layer (PersonService.java) essentially held the business logic associated with individual API calls. Most basic database calls did not need robust business logic, but a few notable operations, like record updating and dealing with null returns from database calls were handled here to ensure API calls to certain functions behaved as a user may expect.
- The controller named and defined the API calls available to users and acted as a connection to the service layer which would actually make calls to the database to retrieve requested information.
- Finally, API calls are made through Postman which acts as the client to make HTTP requests to the controller.

## Architecture
![Spring Boot Architecture](./assets/spring-boot-architecture.png)

# Testing
Aggregation testing was performed manually by comparing API call output to the output of using those same aggregations in both the MongoDB shell or in the MongoDB Compass aggregation interface.
Aggregation testing mostly confirmed the functionality of repository, service, and controller methods, but additional testing was needed to confirm proper output for edge cases. Some examples of this include calls to aggregations that expected an integer return, but sometimes returned null due to no documents in the database matching the aggregation filters. In these cases, some methods had to be modified to return 0 whenever aggregations returned a null value.

# Deployment
The application is available only through this GitHub repository. Downloading the insurance-mongo folder and running the InsuranceApiApplication.java located in insurance-mongo/src/main/java/ca/jrvs/insurance-api is sufficient to begin using this application on your local machine. If you intend to connect this database to an external IP, please see the instructions for changing the IP and Port connect under "Quick Start" above.

# Potential Improvements
- Writing additional built in aggregation queries as API calls to better reflect the kind of information users would want to frequently grab from such a database. 
- Creating a user-friendly front-end to make the user experience with this product a bit more intuitive and friendly without requiring services like Postman in order to make API calls.
- Provide comprehensive documentation detailing how to go about creating additional custom API calls in case users of this product want to create additional custom queries. 
