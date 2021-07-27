# Dockerfile ENV instruction

## Append ENV variable to another ENV variable
```
docker build --tag env-append AppendEnv
docker run --rm env-append
```

## Inherit ENV variable from parent image
EVN variable from a parent image can be used in the child image.
```
docker build -t inherit-env-parent -f InheritEnv/Dockerfile_Parent InheritEnv
docker build -t inherit-env-child -f InheritEnv/Dockerfile_Child InheritEnv
docker run --rm inherit-env-child
```