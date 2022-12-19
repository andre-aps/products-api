FROM openjdk:17-alpine

WORKDIR /app

COPY target/products-api-1.0.jar /app

CMD ["java","-jar","products-api-1.0.jar"]