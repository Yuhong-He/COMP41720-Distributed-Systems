FROM openjdk:8-jre-alpine
COPY target/girlsallowed-*.jar /girlsallowed.jar
CMD ["/usr/bin/java", "-jar", "/girlsallowed.jar", "--registerDestination=http://broker:8083/services", "--serviceUrl=http://girlsallowed:8082/quotations"]
