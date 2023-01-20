# stress-ng

Install: `sudo snap install stress-ng`

Help: `stress-ng -h`
Use 1G of memory: `stress-ng --vm-bytes 1G -m 1`
Use CPU: `stress-ng --cpu 1 -m 1`



stress-ng --cpu 1 --io 4 --vm 2 --vm-bytes 128M --fork 4 --timeout 10s

