FROM openjdk:8-jre-alpine
COPY target/auldfellas-*.jar /auldfellas.jar
CMD ["/usr/bin/java", "-jar", "/auldfellas.jar", "--registerDestination=http://broker:8083/services", "--serviceUrl=http://auldfellas:8080/quotations"]
