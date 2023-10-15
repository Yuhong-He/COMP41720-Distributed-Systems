## Run in local machine
- Open 1st terminal, enter the project root folder, execute ```mvn exec:java -pl broker```
- Open 2nd terminal, enter the project root folder, execute ```mvn exec:java -pl auldfellas -Dexec.args="ADDRESS=localhost"```
- Open 3rd terminal, enter the project root folder, execute ```mvn exec:java -pl dodgygeezers -Dexec.args="ADDRESS=localhost"```
- Open 4th terminal, enter the project root folder, execute ```mvn exec:java -pl girlsallowed -Dexec.args="ADDRESS=localhost"```
- Open 5th terminal, enter the project root folder, execute ```mvn exec:java -pl client -Dexec.args="ADDRESS=localhost"```

## Run in Docker
- Open a terminal, enter the project root folder
- ```docker-compose build```
- ```docker-compose up```

## mvn test
This project is developed under java 11.0.17-amzn, if run ```mvn test``` error, please shift java version to 11.