# Dockerfile ARG instruction

## ARG before FROM
```
docker build \
    --build-arg ALPINE_VERSION=3.12.7 \
    --build-arg PERSON=John \
    --tag arg-before-from \
    ArgBeforeFrom
docker run -it --rm arg-before-from
#Output: Hello, John, from Alpine-3.12.7
```

## Inherit ARG variable from parent image
ARG variable from a parent image can be used in the child image if they are declared with ENV.
```
#Build
docker build \
    --build-arg BEFORE_FROM=111 \
    --build-arg BEFORE_AND_AFTER_FROM=222 \
    --build-arg AFTER_FROM=333 \
    -t inherit-arg-parent \
    -f InheritArg/Dockerfile_Parent \
    InheritArg
docker build -t inherit-arg-child -f InheritArg/Dockerfile_Child InheritArg

# Use in CMD
docker run --rm inherit-arg-child
# Use in exec
docker run --rm inherit-arg-child sh -c 'echo "BEFORE_FROM=${BEFORE_FROM}, BEFORE_AND_AFTER_FROM=${BEFORE_AND_AFTER_FROM}, AFTER_FROM=${AFTER_FROM}"'
```