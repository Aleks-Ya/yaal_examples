# ExternalVolume

## Run
1. Create the volume: `docker volume create my-external-data`
2. Run Docker Compose: `docker compose down && docker compose up`

## Clean up
1. Delete the container: `docker compose down`
2. Delete the volume: `docker volume rm my-external-data`
