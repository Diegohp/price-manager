FROM openjdk:24-jdk-bullseye

# Install necessary tools
RUN apt-get update && apt-get install -y --no-install-recommends \
    curl \
    tar \
    ca-certificates \
 && rm -rf /var/lib/apt/lists/*

# Download and install Maven 3.9.9 from Apache archive
RUN curl -fsSL https://archive.apache.org/dist/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz \
     -o /tmp/maven.tar.gz \
 && tar -xzf /tmp/maven.tar.gz -C /opt \
 && ln -s /opt/apache-maven-3.9.9 /opt/maven \
 && ln -s /opt/maven/bin/mvn /usr/bin/mvn \
 && rm -f /tmp/maven.tar.gz

ENV MAVEN_HOME=/opt/maven
ENV PATH="$MAVEN_HOME/bin:$PATH"

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

COPY target/price-manager-1.0.0.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]