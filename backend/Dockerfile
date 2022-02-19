FROM openjdk:12
FROM maven:latest

WORKDIR /src/msc

# Copy the whole app to the container
COPY . .

# Build and package the app
RUN mvn -Dmaven.test.skip=true -s /usr/share/maven/ref/settings-docker.xml package


ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./target/msc-0.0.1-SNAPSHOT.jar"]