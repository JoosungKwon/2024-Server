FROM --platform=linux/amd64 openjdk:11-oracle
ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar
ENV ACTIVE_PROFILE=${ACTIVE_PROFILE}
ENV JWT_SECRET_KEY=${JWT_SECRET_KEY}
ENV DB_PASSWORD=${DB_PASSWORD}
ADD ${JAR_FILE} docker-springboot.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","/docker-springboot.jar"]