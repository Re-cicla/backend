FROM openjdk:8
EXPOSE 8080
RUN mkdir -p /app/
ADD build/libs/recicla-1.jar /app/recicla-1.jar
ENTRYPOINT ["java", "-jar", "/app/recicla-1.jar"]
