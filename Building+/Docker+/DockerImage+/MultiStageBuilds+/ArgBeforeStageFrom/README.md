## Use ARG before FROM of stage

Build: `docker build --build-arg IMAGE=almalinux:9 -t arg-before-stage-from .`
Run: `docker run -it --rm arg-before-stage-from`
Expected output:
```
London
John
NAME="AlmaLinux"
VERSION="9.0 (Emerald Puma)"
...
```