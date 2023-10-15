# How to run this project

- Open 5 terminals, navigate each to the root folder of task3
- 1 terminal: ```mvn exec:java -pl auldfellas```
- 2 terminal: ```mvn exec:java -pl dodgygeezers```
- 3 terminal: ```mvn exec:java -pl girlsallowed```
- 4 terminal: ```mvn exec:java -pl broker -Dexec.args="http://localhost:9001/quotations http://localhost:9002/quotations http://localhost:9003/quotations"```
- 5 terminal: ```mvn exec:java -pl client```

The project was developed under Java version: Amazon corretto-1.8.0_382, if you want to clean the jar and rebuild package, make sure you are using Java 1.8.