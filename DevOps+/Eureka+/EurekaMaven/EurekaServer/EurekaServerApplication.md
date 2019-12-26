Eureka Server Application by Docker 

Image: https://hub.docker.com/r/netflixoss/eureka

Run:
```
docker run --rm --name eureka \
       --network host \
       -v $PWD/eureka-server-test.properties:/tomcat/webapps/eureka/WEB-INF/classes/eureka-server-test.properties \
       netflixoss/eureka:1.3.1
```

Eureka UI: http://localhost:8080/eureka/
Replica node URL: http://localhost:8080/eureka/v2/