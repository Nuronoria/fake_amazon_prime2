# Use an appropriate base image that includes necessary dependencies and GLIBC version
FROM adoptopenjdk:11

# Install additional libraries if needed
RUN apt-get update && \
    apt-get install -y \
        libgl1-mesa-glx \
        libgtk-3-0 \
        libx11-xcb1 \
        libxtst6 \
        libxrender1 \
        libxi6 \
        libpulse0 \
        && apt-get clean

# Copy JavaFX SDK to the container
COPY library/javafx-sdk-11.x.x /usr/share/openjfx 
 # Replace with the correct version

# Set environment variables for JavaFX
ENV PATH=$PATH:/usr/share/openjfx/bin
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
ENV PATH $JAVA_HOME/bin:$PATH
ENV JAVA_TOOL_OPTIONS="-Djava.library.path=/usr/share/openjfx/lib"

# Copy the application JAR file to the container
COPY login/target/login-1.0-SNAPSHOT.jar /app/login-1.0-SNAPSHOT.jar

# Copy any necessary resources (e.g., FXML files)
COPY login/src/main/resources /app/resources

# Command to run the JavaFX application
CMD ["java", "--module-path", "/usr/share/openjfx/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "/app/login-1.0-SNAPSHOT.jar"]
