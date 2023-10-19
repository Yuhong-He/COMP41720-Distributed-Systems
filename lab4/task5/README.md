# How to run this project

- Open 2 terminals, navigate each to the root folder of task4
- 1st terminal: ```docker-compose up --build```, build and run ActiveMQ and broker services in docker
- 2nd terminal: ```mvn exec:java -pl client```

The project was developed under Java version: Amazon corretto-1.8.0_382, if you want to clean the jar and rebuild package, make sure you are using Java 1.8.