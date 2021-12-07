# !!!NOT FINISHED!!! Oracle in Docker (official image)

Docs: https://github.com/oracle/docker-images/blob/main/OracleDatabase/SingleInstance/README.md#running-oracle-database-21c18c-express-edition-in-a-container

Build image:
1. Download build script:
```
rm -rf /tmp/oracle
mkdir /tmp/oracle
curl -o /tmp/oracle/buildContainerImage.sh \
	https://raw.githubusercontent.com/oracle/docker-images/main/OracleDatabase/SingleInstance/dockerfiles/buildContainerImage.sh
chmod +x /tmp/oracle/buildContainerImage.sh
cd /tmp/oracle
```
2. Run the build: `./buildContainerImage.sh -e -v 21.3.0-xe -o '--build-arg SLIMMING=false'`
3. !!! NOT FINISHED

Run:
```
docker run --name oracle21 \
-p 1521:1521 -p 5500:5500 \
-e ORACLE_PWD=pass1 \
-e ORACLE_CHARACTERSET=AL32UTF8 \
-v /tmp/oracle-xe/ordata:/opt/oracle/oradata \
oracle/database:21.3.0-xe
```
