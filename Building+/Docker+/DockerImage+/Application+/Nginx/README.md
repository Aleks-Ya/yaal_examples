# Nginx

[Official Docker container|https://hub.docker.com/_/nginx/]

## Run
1. `docker run --rm --name some-nginx -p 8080:80 -v /some/content:${pwd}/static_site:ro -d nginx`
2. Open http://localhost:8080

## Stop
`docker stop some-nginx`