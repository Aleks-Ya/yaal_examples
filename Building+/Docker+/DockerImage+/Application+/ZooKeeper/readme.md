# ZooKeeper

[Official Docker container|https://hub.docker.com/_/zookeeper/]

## Single ZooKeeper
### Run
```
# Once
docker run --rm --name some-zookeeper zookeeper

# Service
docker run --name some-zookeeper --restart always -d zookeeper
```
### Connect by CLI
To a local ZooKeeper:
`docker run -it --rm --link some-zookeeper:zookeeper zookeeper zkCli.sh -server zookeeper`
To a remote ZooKeeper:
`docker run -it --rm zookeeper zkCli.sh -server 10.66.131.160:2181`


## ZooKeeper Cluster
### Run
```
docker-compose down
docker-compose up
```
### Connect by CLI
`docker run -it --rm --network zk-cluster-network --name zk-cli-client zookeeper zkCli.sh -server zoo1:2181,zoo2:2181,zoo3:2181`
### Show server status
`docker exec -it zk-1 zkServer.sh status`
