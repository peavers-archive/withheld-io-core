FROM openjdk:8

COPY ${PWD}/build/libs/artifact-0.0.1-SNAPSHOT.jar /app/

CMD ["java", "-Xmx4G", "-jar", "/app/artifact-0.0.1-SNAPSHOT.jar"]