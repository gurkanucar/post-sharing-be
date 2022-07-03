FROM openjdk:11
ARG JAR_FILE=target/post-sharing-be-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} post-sharing-be-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar" , "post-sharing-be-0.0.1-SNAPSHOT.jar"]