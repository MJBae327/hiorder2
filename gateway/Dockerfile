FROM openjdk:11-jre-slim
COPY target/*SNAPSHOT.jar app.jar
EXPOSE 8080

RUN mkdir -p /opt/www/static

ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENTRYPOINT ["java","-Xmx400M","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=docker"]