# Item Repository Spring Boot Application #

### Project Overview ###

This project contains an implementation of a simple repository for Items.


### Application structure ###

The application is implemented as a Spring Boot application using an in memory H2 Database Engine to store the data. 

The application is structured in a controller package with an ItemController that exposes a REST endpoint with one GET Items method. A model package with the data model.

*To check the API documentation use the Swagger.*

The main class in the application is com.cepheid.cloud.skel.SkelApplication. Running this class will start the application and insert four empty Item entities into the database.

### New Features ###

* Added some attributes to the Item class, e.g. a name, a state that can have one of these three values (undefined, valid, invalid) etc.
* Created a new entity class Description, and setup a one to many relation between Item and Description. I.e. an Item has many Descriptions.
* Added Item CRUD methods to the controller.
* Added methods to the controller to query for specific Items based on it's attributes.
* Added Logging, Execption handling and pagination.

### Pending Feature ###
 * Junit Tests

### Installation ###

Clone or download the repository from https://github.com/nick-pk/assignment

Project is configured to use JavaSE-8.

Project is tested using IntelliJ 2020.1 


This is how you import the project into Eclipse:

1. Choose File->New-> Project From Version Control
1. Set Project root directory to where you cloned the repository.
1. Import Options, choose Gradle Wrapper.


### Starting application/ Run Tests ###

Right click on com.cepheid.cloud.skel.SkelApplication and select Run As -> Java Application
Right click on com.cepheid.cloud.skel.ItemControllerTest and select Run As -> JUnit Test

### Useful Links ###

* https://spring.io/projects/spring-boot
* https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
* https://www.h2database.com/html/main.html
