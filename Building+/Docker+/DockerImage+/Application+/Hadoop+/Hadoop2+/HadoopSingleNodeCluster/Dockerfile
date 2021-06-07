ARG HADOOP_VERSION=2.9.2
FROM hadoop-base-image-${HADOOP_VERSION}:1.0
ARG HADOOP_VERSION

RUN yum install -y openssh-clients openssh-server rsync which

ENV HADOOP_DIR=/opt/hadoop
ENV JAVA_HOME=/etc/alternatives/jre

RUN mkdir ${HADOOP_DIR}/input \
  && cp ${HADOOP_DIR}/etc/hadoop/*.xml ${HADOOP_DIR}/input \
  && mv ${HADOOP_DIR}/share/hadoop/mapreduce/hadoop-mapreduce-examples-${HADOOP_VERSION}.jar \
        ${HADOOP_DIR}/share/hadoop/mapreduce/hadoop-mapreduce-examples.jar

WORKDIR ${HADOOP_DIR}

CMD ./bin/hadoop jar \
  ./share/hadoop/mapreduce/hadoop-mapreduce-examples.jar \
  grep ./input ./output 'dfs[a-z.]+'
