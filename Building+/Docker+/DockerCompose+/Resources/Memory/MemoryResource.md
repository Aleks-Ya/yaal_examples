# Memory resources


## Run
Run: `docker-compose down && docker-compose up`
Print memory limit: `docker exec -it memory-container sh -c 'cat /sys/fs/cgroup/memory/memory.limit_in_bytes | numfmt --to=iec'`
Consume memory: `docker exec -it memory-container stress-ng --vm-bytes 1G -m 1`
