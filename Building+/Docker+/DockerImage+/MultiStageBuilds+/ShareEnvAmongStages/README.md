# Sharing ENV between stages

## Using a global ARG instruction
Build: `docker build -t share-env-among-stages-arg UsingArg`
Run: `docker run --rm share-env-among-stages-arg`

## Using a base image
Build: `docker build -t share-env-among-stages-base UsingBaseImage`
Run: `docker run --rm share-env-among-stages-base`
