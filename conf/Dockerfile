FROM java:8

WORKDIR server

COPY riot-server-webservice/target/riot-server-*.jar server.jar

VOLUME /server/config
VOLUME /logs

EXPOSE 8080

CMD ["java", "-cp", "server.jar:config/", "org.springframework.boot.loader.JarLauncher"]
