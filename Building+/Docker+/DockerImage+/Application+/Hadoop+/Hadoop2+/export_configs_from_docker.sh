# Copies Hadoop configs, Kerberos files from Docker container to local dir
# Run (copy to "/tmp"): "./export_configs_from_docker.sh"
# Run (copy to "/tmp/configs"): "./export_configs_from_docker.sh /tmp/configs"

if [ "$1" ]
then
  OUT_DIR=$1
else
  OUT_DIR=/tmp
fi
echo "OUT_DIR: $OUT_DIR"
mkdir -p $OUT_DIR
docker cp hdfs-master:/tmp/kerberos/. ${OUT_DIR}
docker cp hdfs-master:/opt/hadoop/etc/hadoop/core-site.xml ${OUT_DIR}/core-site.xml
docker cp hdfs-master:/opt/hadoop/etc/hadoop/hdfs-site.xml ${OUT_DIR}/hdfs-site.xml
