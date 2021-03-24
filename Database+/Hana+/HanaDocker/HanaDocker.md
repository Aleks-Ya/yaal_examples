# SAP Hana Express Edition in Docker

Image: https://hub.docker.com/_/sap-hana-express-edition

Run: NOT FINISHED
```
docker login
docker run store/saplabs/hanaexpress:2.00.045.00.20200121.1 
```

```
mkdir -p /home/aleks/tmp/hana_docker_data
sudo docker run --rm -p 39013:39013 -p 39017:39017 -p 39041-39045:39041-39045 -p 1128-1129:1128-1129 -p 59013-59014:59013-59014 \
-v /home/aleks/tmp/hana_docker_data:/hana/mounts \
--ulimit nofile=1048576:1048576 \
--sysctl kernel.shmmax=1073741824 \
--sysctl net.ipv4.ip_local_port_range='40000 60999' \
--sysctl kernel.shmmni=524288 \
--sysctl kernel.shmall=8388608 \
--name hana \
store/saplabs/hanaexpress:2.00.045.00.20200121.1 \
--passwords-url file:///hana/mounts/settings.json \
--agree-to-sap-license
```


```
sudo docker run --rm -p 39013:39013 -p 39017:39017 -p 39041-39045:39041-39045 -p 1128-1129:1128-1129 -p 59013-59014:59013-59014 \
--ulimit nofile=1048576:1048576 \
--sysctl kernel.shmmax=1073741824 \
--sysctl net.ipv4.ip_local_port_range='40000 60999' \
--sysctl kernel.shmmni=524288 \
--sysctl kernel.shmall=8388608 \
--name hana \
store/saplabs/hanaexpress:2.00.045.00.20200121.1 \
--agree-to-sap-license
```