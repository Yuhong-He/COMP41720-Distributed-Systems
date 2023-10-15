# How to run this project

Task5 can only work in docker. Steps to run task5:

- In terminal, navigate to the root folder of task5
- Run ```docker-compose up --build```, this command will create image and container in docker to run the services
- Run ```mvn exec:java -pl client```, this command will run the client in localhost

The project was developed under Java version: Amazon corretto-1.8.0_382, if you want to clean the jar and rebuild package, make sure you are using Java 1.8.