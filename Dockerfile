# Build stage - creates the application JAR
FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /workspace/app

# Copy only the files needed for Maven to work (improves layer caching)
COPY pom.xml .
COPY src src

# Build the application
RUN ./mvnw package -DskipTests

# Runtime stage - creates the final optimized image
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /workspace/app/target/*.jar app.jar

# Expose the port your app runs on (change if different)
EXPOSE 8080

# Set timezone (optional, adjust as needed)
ENV TZ=UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]