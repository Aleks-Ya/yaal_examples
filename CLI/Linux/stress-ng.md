# stress-ng

## Install
1. APT: `sudo apt install -y stress-ng`
2. Snap: `sudo snap install stress-ng`
3. Yum: `sudo yum install stress-ng`

Help: `stress-ng -h`
Use 1G of memory: `stress-ng --vm-bytes 1G -m 1 --vm-keep`
Use CPU: `stress-ng --cpu 1 -m 1`



stress-ng --cpu 1 --io 4 --vm 2 --vm-bytes 128M --fork 4 --timeout 10s

