# Distroless images

Sources: https://github.com/GoogleContainerTools/distroless

NOT WORK
Debug (with shell): `docker run -it gcr.io/distroless/static-debian11:debug bash`

## Java 11
Build: `docker build -t distorless-java11 java11`
Run: `docker run -p 8085:8085 -t distorless-java11`
Check: `curl http://localhost:8085/index.html`

## Java 11 + Debug
Build: `docker build -t distorless-java11-debug java11-debug`
Run: `docker run --rm --name distorless-java11-debug -p 8086:8086 -t distorless-java11-debug`
Check: `curl http://localhost:8086/index.html`
Connect with SH: `docker exec -it distorless-java11-debug sh`
