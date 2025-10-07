FROM openjdk:19-jdk-slim
WORKDIR /app
# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port (Render uses PORT env variable)
EXPOSE ${PORT:-8080}

# Run the application
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar target/demo-0.0.1-SNAPSHOT.jar"]