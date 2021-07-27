ARG HIVE_BASE_IMAGE
FROM ${HIVE_BASE_IMAGE}
ARG SPARK_VERSION
ARG HADOOP_BUILD_VERSION

ENV SPARK_DIR=/opt/spark
ENV SPARK_TGZ=/tmp/spark.tgz
ENV SPARK_VERSION=$SPARK_VERSION

RUN wget -O ${SPARK_TGZ} https://archive.apache.org/dist/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION}.tgz \
  && tar -zxvf ${SPARK_TGZ} -C /opt/ \
  && mv /opt/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION} ${SPARK_DIR} \
  && rm -f ${SPARK_TGZ}

WORKDIR ${SPARK_DIR}
