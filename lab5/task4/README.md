# How to run this project

- Open 5 terminal, all enter the project root folder
- 1st terminal, execute ```mvn spring-boot:run -pl broker```
- 2nd terminal, execute ```mvn spring-boot:run -pl auldfellas```
- 3rd terminal, execute ```mvn spring-boot:run -pl dodgygeezers```
- 4th terminal, execute ```mvn spring-boot:run -pl girlsallowed```
- 5th terminal, execute ```mvn exec:java -pl client```, make sure other services are fully executing, then execute this

The project was developed under Java version: Amazon corretto-1.8.0_382, if you want to clean the jar and rebuild package, make sure you are using Java 1.8.
