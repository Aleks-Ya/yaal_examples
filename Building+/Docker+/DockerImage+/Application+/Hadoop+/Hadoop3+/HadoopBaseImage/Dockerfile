FROM centos:8

RUN yum update -y && yum install -y java wget net-tools nano telnet

ARG HADOOP_VERSION

ENV HADOOP_HOME=/opt/hadoop

RUN wget -O /tmp/hadoop.tar.gz http://apache-mirror.rbc.ru/pub/apache/hadoop/common/hadoop-${HADOOP_VERSION}/hadoop-${HADOOP_VERSION}.tar.gz \
  && tar -zxvf /tmp/hadoop.tar.gz -C /opt/ \
  && mv /opt/hadoop-${HADOOP_VERSION} ${HADOOP_HOME} \
  && rm -f /tmp/hadoop.tar.gz

WORKDIR ${HADOOP_HOME}
