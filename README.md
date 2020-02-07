## stokes
Spring Boot application to keep track on the changes of the stocks


## How to Build artifact and run Application:

Application uses in-memory DB: H2
To build Artifact and run Application from command line using maven:

    mvn clean install spring-boot:run

##Rest API Documentation:
    http://localhost:8080/swagger-ui.html

All functions are available via swagger REST

Database content is reachable via:

    http://localhost:8080/h2-console
    