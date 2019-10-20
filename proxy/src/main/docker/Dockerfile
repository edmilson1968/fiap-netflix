FROM openjdk:8-alpine

# environment
EXPOSE 8080

COPY @project.artifactId@-@project.version@.jar app.jar

RUN apk update && apk upgrade && apk add netcat-openbsd &&\
    addgroup -S service && adduser -S booter -G service &&\
    chgrp service app.jar

USER booter
WORKDIR /home/booter

# run app as user 'booter'
ENTRYPOINT ["java", "-Xmx256m", "-Xss32m", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

