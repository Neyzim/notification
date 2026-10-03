from gradle:8-jdk17 as build
WORKDIR /app
copy . .
RUN gradle build --no-daemon

FROM bellsoft/liberica-runtime-container:jdk-17-musl

WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/notification.jar

EXPOSE 8084

CMD ["java", "-jar", "/app/notification.jar"]
