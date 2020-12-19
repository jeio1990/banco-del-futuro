FROM juedizor/oracle-java-8:1.0
ARG ARTIFACT
ARG YML
WORKDIR /home
VOLUME /tmp
COPY ${ARTIFACT} /home/app.jar
COPY ${YML} /home/application.yml
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/home/app.jar"]