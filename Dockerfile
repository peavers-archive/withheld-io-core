FROM openjdk:8

COPY ${PWD}/build/libs/challenge-0.0.1-SNAPSHOT.jar /app/

CMD ["java", "-Xmx4G", "-jar", "/app/challenge-0.0.1-SNAPSHOT.jar"]