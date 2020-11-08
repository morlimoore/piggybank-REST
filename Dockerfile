FROM openjdk:14-alpine

WORKDIR /build/

COPY pom.xml /build/

COPY src /build/src/

ADD target/piggybank-api-v1.jar /build/piggybank-api-v1.jar

EXPOSE 8443

ENTRYPOINT ["java", "-jar", "/build/piggybank-api-v1.jar"]

