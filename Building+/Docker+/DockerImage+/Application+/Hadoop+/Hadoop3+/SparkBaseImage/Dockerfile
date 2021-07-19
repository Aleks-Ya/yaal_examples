ARG HADOOP_IMAGE
FROM ${HADOOP_IMAGE}
ARG SPARK_VERSION
ARG HADOOP_BUILD_VERSION

ENV SPARK_DIR=/opt/spark

RUN yum install -y python36 \
  && wget -O /tmp/spark.tgz http://mirror.linux-ia64.org/apache/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION}.tgz \
  && tar -zxvf /tmp/spark.tgz -C /opt/ \
  && mv /opt/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION} ${SPARK_DIR} \
  && rm -f /tmp/spark.tgz

WORKDIR ${SPARK_DIR}
