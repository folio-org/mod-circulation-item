FROM folioci/alpine-jre-openjdk17:latest

# Install latest patch versions of packages: https://pythonspeed.com/articles/security-updates-in-docker/
USER root
RUN apk upgrade --no-cache
USER folio

# Copy your fat jar to the container; if multiple *.jar files exist the .dockerignore excludes others
COPY target/*.jar ${JAVA_APP_DIR}

# Expose this port locally in the container.
EXPOSE 8081
