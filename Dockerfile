# build stage
#  docker build -t grupo60fiap2023/snackhub-order-app .
#  docker push grupo60fiap2023/snackhub-order-app
FROM gradle:7.6.1-jdk17-alpine AS builder

WORKDIR /usr/app/

COPY . .

RUN gradle bootJar

# build runtime
FROM eclipse-temurin:17.0.5_8-jre-alpine

COPY --from=builder /usr/app/infrastructure/build/libs/*.jar /opt/app/snackhub.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

CMD java -jar /opt/app/snackhub.jar