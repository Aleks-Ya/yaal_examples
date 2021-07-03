export SRC_CONTAINER_DIR=/tmp/files
export SRC_HDFS_DIR=~
export DEST_HDFS_DIR=/tmp/results
export JAR_HDFS_DIR=/tmp/jar
docker run --rm -it \
  --network hadoop-cluster-network \
  --name epam-mapreduce-homework \
  --mount type=bind,source="$PWD"/files,target=$SRC_CONTAINER_DIR \
  --mount type=bind,source="$PWD"/target,target=$JAR_HDFS_DIR \
  hadoop-cluster-master \
  /bin/bash -c \
  "hdfs dfs -mkdir -p ~ \
  && hdfs dfs -put -f $SRC_CONTAINER_DIR/book/*.txt $SRC_HDFS_DIR \
  && yarn jar $JAR_HDFS_DIR/LongestWord-jar-with-dependencies.jar hdfs://master-service:8020 $SRC_HDFS_DIR/The_Genius.txt $DEST_HDFS_DIR \
  && hdfs dfs -ls $DEST_HDFS_DIR \
  && hdfs dfs -cat $DEST_HDFS_DIR/part-r-00000"
