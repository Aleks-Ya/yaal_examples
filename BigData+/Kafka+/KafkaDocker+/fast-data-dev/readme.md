# Run Kafka cluster in Docker with fast-data-dev image

## Sources
Docker Hub: https://hub.docker.com/r/landoop/fast-data-dev

## Run
1. `docker run --rm --net=host lensesio/fast-data-dev`
1. Open http://localhost:3030/

## Run with SSL
1. `docker run --rm --net=host -e ENABLE_SSL=1 lensesio/fast-data-dev`
1. Keys and certificates
    1. In UI: http://localhost:3030/certs/ (password for all: `fastdata`)
        2. Download:
            1. `wget http://localhost:3030/certs/client.jks`
            2. `wget http://localhost:3030/certs/clientA.jks`
            3. `wget http://localhost:3030/certs/clientB.jks`
            4. `wget http://localhost:3030/certs/truststore.jks`
    1. In container: 
        1. `/var/www/certs/clientA.jks`
        2. `/var/www/certs/clientB.jks`
        3. `/var/www/certs/client.jks`
        4. `/var/www/certs/truststore.jks`

## Connect to the container with terminal
`docker run --rm -it --net=host --entrypoint bash lensesio/fast-data-dev`