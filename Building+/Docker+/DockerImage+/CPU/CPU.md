# Limit CPU for a Docker container

No restrictions: `docker run almalinux-updated nproc`
Enable 3 CPUs with numbers 2,5,7: `docker run --cpuset-cpus 2,5,7 almalinux-updated nproc`
