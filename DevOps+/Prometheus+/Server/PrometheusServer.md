# Prometheus Server

## Run in Docker
DockerHub: https://hub.docker.com/r/prom/prometheus
Installation docs: https://prometheus.io/docs/prometheus/latest/installation/#using-docker

### Just run
1. Run container: `docker run --rm -p 9090:9090 prom/prometheus`
2. Open page http://localhost:9090

### Scrap metrics from a Java app
1. Run the spring app:
	1. Run:  `Java+/Monitoring+/Prometheus+/PrometheusSimpleClientHttpServer/src/prometheus/PrometheusApp.java`
	2. Check metrics endpoint: `curl http://localhost:8080`
2. Run Prometheus Server:
	1. Change current dir
	1. Run container: `docker run --rm --net host -v ./prometheus-java.yml:/etc/prometheus/prometheus.yml prom/prometheus`
	2. Open page http://localhost:9090, search for `hello_world_total`

### Scrap metrics from a Spring app
1. Run the spring app:
	1. Run:  `Java+/Spring+/SpringBoot+/SpringBoot2+/Actuator+/MicrometerPrometheus/src/micrometer/MicrometerApp.java`
	2. Check metrics endpoint: `curl http://localhost:8080/actuator/prometheus`
2. Run Prometheus Server:
	1. Change current dir
	1. Run container: `docker run --rm --net host -v ./prometheus-spring.yml:/etc/prometheus/prometheus.yml prom/prometheus`
	2. Open page http://localhost:9090
