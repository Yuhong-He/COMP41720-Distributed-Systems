# How to run this project

- Open 6 terminals, navigate each to the root folder of task4
- 1st terminal: ```docker-compose up --build```, build and run ActiveMQ service in docker
- 2nd terminal: ```mvn exec:java -pl auldfellas```
- 3rd terminal: ```mvn exec:java -pl dodgygeezers```
- 4th terminal: ```mvn exec:java -pl girlsallowed```
- 5th terminal: ```mvn exec:java -pl broker```
- 6th terminal: ```mvn exec:java -pl client```

The project was developed under Java version: Amazon corretto-1.8.0_382, if you want to clean the jar and rebuild package, make sure you are using Java 1.8.