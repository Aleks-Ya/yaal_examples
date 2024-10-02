# Grafana Alloy CLI

Install: get install command from https://alexhome.grafana.net/a/grafana-collector-app

Help: `alloy --help`
Sub-command help: `alloy fmt --help`

Show service status: `sudo systemctl status alloy`

Debug UI: http://localhost:12345

## Configuration
Default config: `/etc/default/alloy`

Change configuration:
1. Edit config `/etc/alloy/config.alloy` (specified in default config `/etc/default/alloy`)
2. Restart service: `sudo systemctl reload alloy`
