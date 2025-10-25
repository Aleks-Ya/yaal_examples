# watch CLI

Install: `yum install procps-ng`

Help: `watch -h`

Show command output every 2 seconds: `watch free -m`
Highlight changed symbols: `watch -d free -m`
Watch command containing `$`: `watch 'echo $(date)'`
