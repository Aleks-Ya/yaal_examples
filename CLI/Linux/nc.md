# nc (netcat) CLI

Help: `nc -h`

Response for a GET request with "200 OK":
1. Server: `echo -e "HTTP/1.1 200 OK\r\nContent-Length: 5\r\n\r\nHello" | nc -lk -p 8089`
2. Client: `curl http://localhost:8089/`

Response for a GET request with "204 No Content":
1. Server: `echo -ne "HTTP/1.1 204 No Content\r\n\r\n" | nc -lk -p 8089`
2. Client: `curl http://localhost:8089/`

Response for a POST request with "200 OK":
1. Server: `echo -e "HTTP/1.1 200 OK\r\nContent-Length: 5\r\n\r\nHello" | nc -lk -p 8089`
2. Client: `curl -X POST -H "Content-Type: plain/text" -d 'The body' http://localhost:8089/`

Response for many requests:
1. Server: `while true; do echo -e "HTTP/1.1 200 OK\r\nContent-Length: 2\r\n\r\nOK" | nc -lN 8089; done`
2. Client (many times): `curl http://localhost:8089`
