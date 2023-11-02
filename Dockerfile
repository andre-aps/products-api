#Stage 1
FROM maven:3.8.1-openjdk-17-slim as build
WORKDIR /app
COPY . /app
RUN ["mvn","clean","package"]

#Stage 2
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/products-api-1.0.0.jar /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "products-api-1.0.0.jar"]
