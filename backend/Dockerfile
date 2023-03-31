FROM maven:3.8.1-jdk-8-slim AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/

RUN mvn package -DskipTests

FROM openjdk:8-jre

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/acc-system-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "acc-system-0.0.1-SNAPSHOT.jar"]