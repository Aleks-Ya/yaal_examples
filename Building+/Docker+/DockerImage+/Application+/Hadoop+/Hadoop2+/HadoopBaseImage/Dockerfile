FROM centos:7

RUN yum update -y && yum install -y java wget net-tools nano telnet less which

ARG HADOOP_VERSION

ENV HADOOP_PREFIX=/opt/hadoop

RUN wget -O /tmp/hadoop.tar.gz http://apache-mirror.rbc.ru/pub/apache/hadoop/common/hadoop-${HADOOP_VERSION}/hadoop-${HADOOP_VERSION}.tar.gz \
  && tar -zxvf /tmp/hadoop.tar.gz -C /opt/ \
  && mv /opt/hadoop-${HADOOP_VERSION} ${HADOOP_PREFIX} \
  && rm -f /tmp/hadoop.tar.gz

WORKDIR ${HADOOP_PREFIX}
