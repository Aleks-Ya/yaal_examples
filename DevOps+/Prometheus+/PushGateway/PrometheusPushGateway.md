# Prometheus Push Gateway

GitHub: https://github.com/prometheus/pushgateway
DockerHub: https://hub.docker.com/r/prom/pushgateway

## Run
1. Run PushGateway: 
	1. Run: `docker run --rm --net host prom/pushgateway`
	2. Check availability: http://localhost:9091
2. Run PrometheusServer: 
	1. Change directory to current
	2. Run: `docker run --rm --net host -v ./prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus`
	3. Check availability: http://localhost:9090
3. Test
	1. Send a test metric to PushGateway: `curl -d $'my_metric 123.4\n' http://localhost:9091/metrics/job/my_job`
	2. See metric in PushGateway: http://localhost:9091
	3. See metric in PrometheusSerever: 
		1. Using REST API: `curl http://localhost:9090/api/v1/query?query=my_metric`
		1. Using UI (search for `my_metric`): http://localhost:9090
