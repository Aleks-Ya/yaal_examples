# ZooKeeper

[Official Docker container|https://hub.docker.com/_/zookeeper/]

## Run
```
# Once
docker run --rm --name some-zookeeper zookeeper

# Service
docker run --name some-zookeeper --restart always -d zookeeper
```

## Connect by CLI
### To a local ZooKeeper
`docker run -it --rm --link some-zookeeper:zookeeper zookeeper zkCli.sh -server zookeeper`
### To a remote ZooKeeper
`docker run -it --rm zookeeper zkCli.sh -server 10.66.131.160:2181`
