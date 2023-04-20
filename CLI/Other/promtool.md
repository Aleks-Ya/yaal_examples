# Prometheus PromTool

Install: `brew install prometheus`

Help: `promtool -h`
Version: `promtool --version`

Send an instant query: `promtool query instant http://localhost:9090 'up'`
Check metrics: `curl -s http://localhost:9090/metrics | promtool check metrics`
