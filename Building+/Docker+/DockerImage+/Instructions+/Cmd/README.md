# Dockerfile CMD instruction

## The "exec" form (preferred): `CMD ["executable", "param1", "param2"]`
```
# Build
docker build --tag instruction-cmd-exec -f Dockerfile_Exec .

# Run and remove
docker run --rm instruction-cmd-exec

# Run, start and remove
docker run --name instruction-cmd-exec instruction-cmd-exec
docker start -a instruction-cmd-exec
docker rm instruction-cmd-exec
```

## The "shell" form: `CMD command param1 param2`
```
# Build
docker build --tag instruction-cmd-shell -f Dockerfile_Shell .

# Run and remove
docker run --rm instruction-cmd-shell

# Run, start and remove
docker run --name instruction-cmd-shell instruction-cmd-shell
docker start -a instruction-cmd-shell
docker rm instruction-cmd-shell
```

## The "entrypoint" form (as default parameters to ENTRYPOINT)
```
# Build
docker build --tag instruction-cmd-entrypoint -f Dockerfile_EntryPoint .

# Run and remove
docker run --rm instruction-cmd-entrypoint

# Run, start and remove
docker run --name instruction-cmd-entrypoint instruction-cmd-entrypoint
docker start -a instruction-cmd-entrypoint
docker rm instruction-cmd-exec
```


#Run
docker run --name instruction-cmd-shell instruction-cmd-shell
docker start -a instruction-cmd-shell
docker rm instruction-cmd-shell
