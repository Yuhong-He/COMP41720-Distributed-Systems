# How to run this project

The project was developed under Java version: Amazon corretto-1.8.0_382, if you want to clean the jar and rebuild package, make sure you are using Java 1.8.

## Run in Docker
- Open 2 terminal, both enter the project root folder
- 1st terminal, execute ```docker-compose up --build```, make sure this service is fully executing, then execute next
- 2nd terminal, execute ```mvn exec:java -pl client```

## Run in local machine
- Open 5 terminal, all enter the project root folder
- 1st terminal, execute ```mvn spring-boot:run -pl broker```, make sure this service is fully executing, then execute others
- 2nd terminal, execute ```mvn spring-boot:run -pl auldfellas```
- 3rd terminal, execute ```mvn spring-boot:run -pl dodgygeezers```
- 4th terminal, execute ```mvn spring-boot:run -pl girlsallowed```
- 5th terminal, execute ```mvn exec:java -pl client```, make sure other services are fully executing, then execute this

### More interesting things
- If you want to register another service url rather than itself, try ```mvn spring-boot:run -pl auldfellas -Dspring-boot.run.arguments="--registerDestination=http://localhost:8083/services --serviceUrl=https://www.google.ie"``` (Only working in local machine mode)
- If you want to check all the registered services, visit ```http://localhost:8083/services``` in a browser window