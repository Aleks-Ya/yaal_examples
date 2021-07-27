FROM centos:7

RUN yum update -y && yum install -y java wget net-tools nano telnet less which
ENV JAVA_HOME=/etc/alternatives/jre

ARG HADOOP_VERSION
ENV HADOOP_VERSION=$HADOOP_VERSION
ENV HADOOP_PREFIX=/opt/hadoop
ENV HADOOP_LOG_DIR=/tmp/logs
ENV HADOOP_CONF_DIR=${HADOOP_PREFIX}/etc/hadoop

RUN useradd -m hadoop
USER hadoop

RUN mkdir -p ${HADOOP_LOG_DIR} \
  && wget -O /tmp/hadoop.tar.gz http://apache-mirror.rbc.ru/pub/apache/hadoop/common/hadoop-${HADOOP_VERSION}/hadoop-${HADOOP_VERSION}.tar.gz \
  && tar -zxvf /tmp/hadoop.tar.gz -C /opt/ \
  && mv /opt/hadoop-${HADOOP_VERSION} ${HADOOP_PREFIX} \
  && rm -f /tmp/hadoop.tar.gz \
  && chmod -R g+rw ${HADOOP_CONF_DIR} ${HADOOP_LOG_DIR}

WORKDIR ${HADOOP_PREFIX}
