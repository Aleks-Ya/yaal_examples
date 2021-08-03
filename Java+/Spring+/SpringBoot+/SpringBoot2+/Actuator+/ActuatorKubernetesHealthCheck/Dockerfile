FROM java:8-jre-alpine

ADD target/actuator-health-check-kubernetes-*.jar /app.jar

EXPOSE 9999
EXPOSE 8080

CMD java -jar /app.jar
