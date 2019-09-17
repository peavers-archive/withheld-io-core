FROM hisplan/alpine-java-python-perl

COPY ./build/libs/challenge-0.0.1-SNAPSHOT.jar /app/
COPY ./src/main/resources/cloc-1.82.pl /app/

CMD ["java", "-Xmx4G", "-jar", "/app/challenge-0.0.1-SNAPSHOT.jar"]

