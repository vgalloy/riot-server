FROM java:8

WORKDIR server

VOLUME /server/config
VOLUME /logs

EXPOSE 8080

COPY riot-server-webservice/build/libs/3csminute*.jar server.jar

CMD ["java", "jar", "server.jar"]
