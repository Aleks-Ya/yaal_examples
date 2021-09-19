ARG HADOOP_BASE_IMAGE
FROM $HADOOP_BASE_IMAGE

USER hdfs
RUN mkdir -p /home/hdfs/namenode \
    && mkdir -p /home/hdfs/datanode
ENV PATH=$PATH:$HADOOP_PREFIX/bin:$HADOOP_PREFIX/sbin
ENV HADOOP_ENV_SH=${HADOOP_CONF_DIR}/hadoop-env.sh

ADD --chown=hadoop:hadoop *.xml ${HADOOP_CONF_DIR}/
ADD --chown=hadoop:hadoop *.sh ${HADOOP_PREFIX}/

RUN sed -i "s@\${JAVA_HOME}@${JAVA_HOME}@g" ${HADOOP_ENV_SH} \
  && cat ${HADOOP_PREFIX}/hadoop-env-append.sh >> ${HADOOP_ENV_SH}

USER root

EXPOSE 1006

WORKDIR ${HADOOP_PREFIX}
