# How to run this project
### Run in docker
- In terminal, navigate to the root folder of task4
- Run ```docker-compose up --build```, this command will create image and container in docker to run the services
- Run ```mvn exec:java -pl client```, this command will run the client in localhost

### Run in localhost
- Open 5 terminals, navigate each to the root folder of task4
- 1 terminal: ```mvn exec:java -pl auldfellas```
- 2 terminal: ```mvn exec:java -pl dodgygeezers```
- 3 terminal: ```mvn exec:java -pl girlsallowed```
- 4 terminal: ```mvn exec:java -pl broker -Dexec.args="http://localhost:9001/quotations http://localhost:9002/quotations http://localhost:9003/quotations"```
- 5 terminal: ```mvn exec:java -pl client```

### Other Info
The project was developed under Java version: Amazon corretto-1.8.0_382, if you want to clean the jar and rebuild package, make sure you are using Java 1.8.