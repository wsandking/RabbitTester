FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD DummyMircroservice*.jar app.jar
RUN sh -c 'touch /app.jar'
RUN sh -c 'mkdir -p /var/log/hazelcast-server'
# ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
# ENTRYPOINT ["java","-Dcom.sun.net.ssl.checkRevocation=false","-jar","/app.jar"]
# ENTRYPOINT ["/bin/sleep", "4h"]
ENTRYPOINT ["java","-jar","/app.jar"]
