# TinyProxy

## Run
On port 2121:
1. Run: `docker run -it --rm --name tinyproxy -p 2121:8888 dannydirect/tinyproxy:latest ANY`
2. Test: `curl -x http://localhost:2121 -I http://example.com`

On port 21:
1. Run: `sudo docker run -it --rm --name tinyproxy -p 127.0.0.1:21:8888 dannydirect/tinyproxy:latest ANY`
2. Test: `curl -x http://localhost:21 -I http://example.com`
