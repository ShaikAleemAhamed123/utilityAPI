FROM openjdk:18-jdk-alpine
RUN mvn clean package
COPY target/utilityAPI-0.0.1-SNAPSHOT.jar utilityAPI-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/utilityAPI-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080