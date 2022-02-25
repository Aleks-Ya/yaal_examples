# curl CLI

Download to specific file:
```
curl -o output.zip http://data.com/file.zip
```
Send POST request with JSON body:
```
curl -X POST \
	-H "Content-Type: application/json" \
	-d '{"name": "John"}' \
	http://localhost:9000/json
```
Print response status and headers:
```
curl -i http://ya.ru
```
Verbose:
```
curl --verbose https://google.com
```
Basic auth:
```
curl -u username:password https://google.com
```
Ignore certificate errors:
```
curl --insecure https://google.com
```
Don't show progress:
```
curl -i http://ya.ru
```
