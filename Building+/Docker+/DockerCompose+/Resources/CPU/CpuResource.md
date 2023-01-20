# CPU resources


## Run
Run: `docker-compose down && docker-compose up`
Print CPU limit: `docker exec -it cpu-container cat /sys/fs/cgroup/cpu/cpuacct.stat`
Show current CPU usage: `docker exec -it cpu-container top -b -d 5`
Consume CPU: `docker exec -it cpu-container stress-ng --cpu 1 -m 1`
