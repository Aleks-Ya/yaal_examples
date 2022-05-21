# Stop build at specific stage

Docs: https://docs.docker.com/develop/develop-images/multistage-build/#stop-at-a-specific-build-stage

Build all stages: `docker build -t stop-at-stage .`
Build `people` stage: `docker build --target people -t stop-at-stage .`
Build `cars` stage: `docker build --target cars -t stop-at-stage .`

Run (any): `docker run --rm stop-at-stage ls /data`
