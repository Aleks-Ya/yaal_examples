FROM centos:7

LABEL description="Setup a Hadoop Single Node Cluster in Local Mode and \
run a standard example application."

RUN yum install -y java ssh rsync wget tar which

ENV JAVA_HOME=/etc/alternatives/jre
ENV HADOOP_DIR=/opt/hadoop
ENV HADOOP_VERSION=2.9.2

RUN wget -O /tmp/hadoop.tar.gz http://mirror.linux-ia64.org/apache/hadoop/common/hadoop-${HADOOP_VERSION}/hadoop-${HADOOP_VERSION}.tar.gz \
  && tar -zxvf /tmp/hadoop.tar.gz -C /opt/ \
  && mv /opt/hadoop-${HADOOP_VERSION} /opt/hadoop \
  && rm -f /tmp/hadoop.tar.gz

RUN mkdir ${HADOOP_DIR}/input \
  && cp ${HADOOP_DIR}/etc/hadoop/*.xml ${HADOOP_DIR}/input

WORKDIR ${HADOOP_DIR}
ENTRYPOINT ./bin/hadoop jar ./share/hadoop/mapreduce/hadoop-mapreduce-examples-${HADOOP_VERSION}.jar grep ./input ./output 'dfs[a-z.]+'
