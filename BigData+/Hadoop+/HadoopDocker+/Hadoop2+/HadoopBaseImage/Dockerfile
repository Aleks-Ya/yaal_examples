FROM centos-updated:7
ARG HADOOP_VERSION

RUN yum install -y java
ENV JAVA_HOME=/etc/alternatives/jre

ENV HADOOP_VERSION=$HADOOP_VERSION
ENV HADOOP_PREFIX=/opt/hadoop
ENV HADOOP_HOME=${HADOOP_PREFIX}
ENV HADOOP_LOG_DIR=/tmp/logs
ENV YARN_LOG_DIR=${HADOOP_LOG_DIR}/yarn
ENV HADOOP_CONF_DIR=${HADOOP_PREFIX}/etc/hadoop
ENV HADOOP_ENV_SH=${HADOOP_CONF_DIR}/hadoop-env.sh

RUN useradd -m hadoop && \
    useradd -m -g hadoop hdfs && \
    useradd -m -g hadoop yarn && \
    useradd -m -g hadoop mapred

USER hadoop
RUN mkdir -p ${HADOOP_LOG_DIR} && \
    mkdir -p ${YARN_LOG_DIR} && \
    wget -O /tmp/hadoop.tar.gz http://apache-mirror.rbc.ru/pub/apache/hadoop/common/hadoop-${HADOOP_VERSION}/hadoop-${HADOOP_VERSION}.tar.gz && \
    tar -zxvf /tmp/hadoop.tar.gz -C /tmp/ && \
    mv /tmp/hadoop-${HADOOP_VERSION} ${HADOOP_PREFIX} && \
    rm -f /tmp/hadoop.tar.gz && \
    chmod -R g+rw ${HADOOP_CONF_DIR} ${HADOOP_LOG_DIR}

WORKDIR ${HADOOP_PREFIX}
