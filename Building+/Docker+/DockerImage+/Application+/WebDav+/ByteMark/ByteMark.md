# ByteMark WebDav server

Docker Hub: https://hub.docker.com/r/bytemark/webdav
GitHub: https://github.com/BytemarkHosting/docker-webdav

## Run server
1. Run Docker server: `docker run --rm -e AUTH_TYPE=Digest -e USERNAME=alice -e PASSWORD=secret1234 -p 80:80 -d bytemark/webdav`
2. Test connection: `cadaver http://localhost`
