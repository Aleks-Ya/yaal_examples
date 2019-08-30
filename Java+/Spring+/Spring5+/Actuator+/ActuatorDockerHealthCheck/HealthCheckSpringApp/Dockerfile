FROM java:8-jre-alpine

ADD target/actuator-health-check-*.jar /app.jar

EXPOSE 8080

HEALTHCHECK --interval=10s --timeout=3s \
              CMD wget -q -s http://localhost:8080/actuator/health || exit 1

CMD java -jar /app.jar
