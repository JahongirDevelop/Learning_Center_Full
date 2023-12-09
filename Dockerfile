FROM openjdk:19
EXPOSE 3000
ARG JAR_FILE=build/libs/Learning_center_full-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} learning_center_full
ENTRYPOINT ["java","-jar", "learning_center_full"]