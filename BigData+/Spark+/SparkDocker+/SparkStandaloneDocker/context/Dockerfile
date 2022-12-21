FROM almalinux-updated

RUN yum install -y java python38

ARG SPARK_VERSION
ENV SPARK_DIR=/opt/spark
ENV SPARK_TGZ=/tmp/spark.tgz
ENV HADOOP_BUILD_VERSION=3

RUN wget -O ${SPARK_TGZ} https://archive.apache.org/dist/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION}.tgz \
  && tar -zxvf ${SPARK_TGZ} -C /opt/ \
  && mv /opt/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION} ${SPARK_DIR} \
  && rm -f ${SPARK_TGZ}

ENV SPARK_HOME=/opt/spark
ENV SPARK_CONF_DIR=${SPARK_HOME}/conf
ENV PATH=$PATH:$SPARK_HOME/bin::$SPARK_HOME/sbin

ADD spark-defaults.conf ${SPARK_CONF_DIR}/
ADD *.sh ${SPARK_HOME}/

WORKDIR ${SPARK_HOME}
