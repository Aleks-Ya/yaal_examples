ARG HADOOP_BASE_IMAGE
FROM $HADOOP_BASE_IMAGE

USER root
RUN yum install -y krb5-workstation krb5-libs \
    && useradd -m -g hadoop hdfs

USER hdfs
ENV PATH=$PATH:$HADOOP_PREFIX/bin:$HADOOP_PREFIX/sbin
ENV HADOOP_ENV_SH=${HADOOP_CONF_DIR}/hadoop-env.sh

RUN mkdir -p ${HADOOP_LOG_DIR}

ADD --chown=hadoop:hadoop *.xml ${HADOOP_CONF_DIR}/
ADD --chown=hadoop:hadoop *.sh ${HADOOP_PREFIX}/

RUN sed -i "s@\${JAVA_HOME}@${JAVA_HOME}@g" ${HADOOP_ENV_SH} \
  && cat ${HADOOP_PREFIX}/hadoop-env-append.sh >> ${HADOOP_ENV_SH}

ENV KRB5CCNAME=/tmp/krb5cc

WORKDIR ${HADOOP_PREFIX}
