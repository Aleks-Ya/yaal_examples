# curl CLI

## Install
Version 7: `sudo apt install curl`
Version 8: `sudo snap install curl`

## Commands
Help: `curl -h`
Version: `curl --version`
Download to specific file: `curl -o output.zip http://data.com/file.zip`
Send POST request with JSON body:
```shell
curl -X POST \
	-H "Content-Type: application/json" \
	-d '{"name": "John"}' \
	http://localhost:9000/json
```
Print response status and headers: `curl -i http://ya.ru`
Verbose: `curl --verbose https://google.com`
Basic auth: `curl -u username:password https://google.com`
Ignore certificate errors: `curl --insecure https://google.com`
Don't show progress: `curl -s http://ya.ru`
Connect timeout (sec): `curl -m 60 http://localhost:80/internal/v1/tenant/info/ALL`
Connection timeout (sec): `curl --connect-timeout 60 http://localhost:80/internal/v1/tenant/info/ALL`
Follow redirection: `curl -L http://ya.ru`
Send a HEAD request (content size): `curl -I https://httpbin.io/bytes/500`
Read body from a file: `curl http://httpbin.io/post -d @body.txt`
