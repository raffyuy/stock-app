FROM amazoncorretto:17-alpine-jdk
WORKDIR /app

ARG JAR_FILE=target/stockapi-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app/app.jar

ARG INPUT_FILE=target/classes/input.csv
COPY ${INPUT_FILE} /app

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
