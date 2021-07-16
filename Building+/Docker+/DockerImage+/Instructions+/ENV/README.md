# Dockerfile ENV instruction

## Append to another environment variable: `CMD ["executable", "param1", "param2"]`
```
# Build
docker build --tag env-append -f Dockerfile_Append_To_Another_Var .

# Run and remove
docker run --rm --name env-append env-append
```
