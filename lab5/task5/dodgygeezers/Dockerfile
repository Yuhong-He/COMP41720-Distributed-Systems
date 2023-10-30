FROM openjdk:8-jre-alpine
COPY target/dodgygeezers-*.jar /dodgygeezers.jar
CMD ["/usr/bin/java", "-jar", "/dodgygeezers.jar", "--registerDestination=http://broker:8083/services", "--serviceUrl=http://dodgygeezers:8081/quotations"]
