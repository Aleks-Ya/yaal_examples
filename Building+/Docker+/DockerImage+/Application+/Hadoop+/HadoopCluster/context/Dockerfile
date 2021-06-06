ARG HADOOP_VERSION=2.10.1
FROM spark-base-image-2.4.8-hadoop-${HADOOP_VERSION}:1.0
ARG HADOOP_VERSION

RUN yum install -y which net-tools nano telnet less

ENV JAVA_HOME=/etc/alternatives/jre
ENV HADOOP_PREFIX=/opt/hadoop
ENV HADOOP_HOME=${HADOOP_PREFIX}
ENV SPARK_HOME=/opt/spark
ENV SPARK_CONF_DIR=${SPARK_HOME}/conf
ENV HADOOP_CONF_DIR=${HADOOP_PREFIX}/etc/hadoop
ENV HADOOP_LOG_DIR=/tmp/logs
ENV HADOOP_VERSION=${HADOOP_VERSION}
ENV PATH=$PATH:$HADOOP_PREFIX/bin:$HADOOP_PREFIX/sbin:$SPARK_HOME/bin::$SPARK_HOME/sbin
ENV YARN_LOG_DIR=${HADOOP_LOG_DIR}/yarn
ENV HADOOP_ENV_SH=${HADOOP_CONF_DIR}/hadoop-env.sh

ADD *.xml ${HADOOP_CONF_DIR}/
ADD spark-defaults.conf ${SPARK_CONF_DIR}/
ADD *.sh ${HADOOP_PREFIX}/
ADD java-opts ${HADOOP_CONF_DIR}/

RUN mkdir -p ${HADOOP_LOG_DIR} \
  && mkdir -p ${YARN_LOG_DIR} \
  && sed -i "s@\${JAVA_HOME}@${JAVA_HOME}@g" ${HADOOP_ENV_SH} \
  && cat ${HADOOP_PREFIX}/hadoop-env-append.sh >> ${HADOOP_ENV_SH}

WORKDIR ${HADOOP_PREFIX}
