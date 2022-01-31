# Alpine Linux with Python 2

Choose Alpine version: `export V=3`
Build: `docker build --build-arg VERSION=$V -t alpine-python2:$V .`
Check: `docker run -it --rm --name alpine-python2-$V alpine-python2:$V bash -c "python -V; pip -V"`
Run Bash: `docker run -it --rm --name alpine-python2-$V alpine-python2:$V`

