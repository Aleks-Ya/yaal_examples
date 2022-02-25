# HiveServer2 CLI

Help:
```
hiveserver2 --help
```
(NOT WORK) Configure logging:
```
hiveserver2 --hiveconf hive.root.logger=DEBUG,DRFA
hiveserver2 \
    --hiveconf hive.server2.logging.operation.enabled=true \
    --hiveconf hive.server2.logging.operation.verbose=true \
    --hiveconf hive.server2.logging.operation.level=DEBUG \
    --hiveconf hive.server2.logging.operation.log.location=/tmp/hs2.log
```
