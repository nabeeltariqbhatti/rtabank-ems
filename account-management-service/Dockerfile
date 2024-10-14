# Use Maven image to build the project
FROM maven:3.9.8 AS builder

# on server bound the vo
WORKDIR /app

# Copy the pom.xml and other necessary files
COPY pom.xml ./
COPY src ./src
RUN mvn dependency:go-offline
# Build the project
RUN mvn package -DskipTests

# Use a base image with JDK for building
FROM openjdk:21 AS jdk

WORKDIR /app
# Create a minimal JRE including all modules using jlink
RUN jlink --module-path $JAVA_HOME/jmods \
          --add-modules ALL-MODULE-PATH \
          --output myapp-runtime \
          --strip-debug \
          --compress=2 \
          --no-header-files \
          --no-man-pages

# Use a minimal base image for the runtime
FROM debian:bullseye-slim

# Set the working directory
WORKDIR /app

# Copy the custom JRE from the build stage
COPY --from=jdk /app/myapp-runtime /app/myapp-runtime

# Copy the application JAR from the build stage
COPY --from=builder /app/target/*.jar /app/myapp.jar

# Set the JAVA_HOME environment variable
ENV JAVA_HOME=/app/myapp-runtime
ENV PATH="${JAVA_HOME}/bin:${PATH}".
EXPOSE 9095
# Specify the command to run the application
CMD ["java", "-jar", "myapp.jar"]
