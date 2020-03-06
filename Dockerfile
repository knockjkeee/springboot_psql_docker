FROM openjdk:8

MAINTAINER test.com

#WORKDIR /opt/app

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/demo-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

#COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]