FROM openjdk:8-alpine

# environment
EXPOSE 8080

# executable
ADD @project.artifactId@-@project.version@.jar app.jar

# create group
#RUN groupadd service

# create user
RUN addgroup -S service && adduser -S booter -G service
RUN chgrp service app.jar

USER booter
WORKDIR /home/booter

# run app as user 'booter'
ENTRYPOINT ["java", "-Xmx256m", "-Xss32m", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
