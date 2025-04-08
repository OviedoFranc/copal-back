FROM maven:3.8.6-openjdk-18 AS build
COPY . .
RUN mvn clean package assembly:single -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /target/socios.back-0.0.1-SNAPSHOT.jar socios.jar
EXPOSE 8080
CMD [ "java","-jar","socios.jar" ]