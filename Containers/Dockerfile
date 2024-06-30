FROM adoptopenjdk:17-jdk-hotspot


RUN apt-get update && \
    apt-get install -y libx11-6 libgl1 libglib2.0-0 libgtk-3-0 libcanberra-gtk-module && \
    apt-get install -y maven


ENV JAVA_HOME /opt/java/openjdk


WORKDIR /app


COPY library/javafx-sdk-22.0.1 /opt/javafx-sdk-22.0.1


ENV PATH=$PATH:/opt/javafx-sdk-22.0.1/bin
ENV JAVA_OPTS="-Djava.library.path=/opt/javafx-sdk-22.0.1/lib"


COPY login/pom.xml ./
COPY login/src ./src


RUN mvn clean package


COPY login/target/login-1.0-SNAPSHOT.jar /app/login-1.0-SNAPSHOT.jar


CMD ["java", "--module-path", "/opt/javafx-sdk-22.0.1/lib", "--add-modules", "javafx.controls,javafx.fxml", "-Djava.library.path=/opt/javafx-sdk-22.0.1/lib", "-jar", "login-1.0-SNAPSHOT.jar"]
