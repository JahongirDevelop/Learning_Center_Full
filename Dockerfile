FROM openjdk:19
EXPOSE 8080
ARG JAR_FILE=build/libs/Learning_center_full-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} my_app
ENTRYPOINT ["java","-jar", "my_app"]