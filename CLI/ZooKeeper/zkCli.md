# zkCli.sh CLI

Connect to any Zookeeper server
Connect to server 10.66.131.160:
```
docker run -it --rm zookeeper zkCli.sh -server 10.66.131.160:2181
```

Commands

Help: 
```
help
```
Show root ZNodes: 
```
ls /
```
Show value of ZNode: 
```
get /hbase/master
```
Show config:
```
config
```
Exit:
```
quit
```
