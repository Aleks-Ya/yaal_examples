ARG HADOOP_IMAGE=hadoop-base-image-2.9.2:1.1
FROM ${HADOOP_IMAGE}
ARG HADOOP_IMAGE
ARG SPARK_VERSION=2.4.0

ENV SPARK_DIR=/opt/spark

RUN wget -O /tmp/spark.tgz http://mirror.linux-ia64.org/apache/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop2.7.tgz \
  && tar -zxvf /tmp/spark.tgz -C /opt/ \
  && mv /opt/spark-${SPARK_VERSION}-bin-hadoop2.7 ${SPARK_DIR} \
  && rm -f /tmp/spark.tgz

WORKDIR ${SPARK_DIR}
