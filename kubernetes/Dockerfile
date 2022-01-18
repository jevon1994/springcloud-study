FROM java

WORKDIR /root

ADD ./target/kubernetes-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]