# Alpine Linux with Python 3

Choose Alpine version: `export V=3`
Build: `docker build --build-arg VERSION=$V -t alpine-python3:$V .`
Check: `docker run -it --rm --name alpine-python3-$V alpine-python3:$V bash -c "python3 -V; pip3 -V"`
Run Bash: `docker run -it --rm --name alpine-python3-$V alpine-python3:$V`

