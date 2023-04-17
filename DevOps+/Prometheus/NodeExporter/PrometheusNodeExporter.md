# Prometheus Node Exporter

GitHub: https://github.com/prometheus/node_exporter
DockerHub: https://hub.docker.com/r/prom/node-exporter

## Run
1. Run NodeExporter: 
	1. Run: `docker run --rm --net host --pid host -v "/:/host:ro,rslave" prom/node-exporter --path.rootfs=/host`
	2. Check metrics: http://localhost:9100
2. Run PrometheusServer: 
	1. Change directory to current
	2. Run: `docker run --rm -p 9090:9090 --net host -v ./prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus`
	3. Check availability: http://localhost:9090
3. Check metric in PrometheusSerever: 
	1. Using REST API: `curl http://localhost:9090/api/v1/query?query=node_cpu_seconds_total`
	2. Using UI (search for `node_cpu_seconds_total`): http://localhost:9090
