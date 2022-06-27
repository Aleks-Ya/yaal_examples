ARG HADOOP_IMAGE

FROM almalinux-updated as spark-distr
ARG SPARK_VERSION
ENV SPARK_VERSION=${SPARK_VERSION}
ARG HADOOP_BUILD_VERSION
ENV HADOOP_BUILD_VERSION=${HADOOP_BUILD_VERSION}
RUN wget -O /tmp/spark.tgz http://mirror.linux-ia64.org/apache/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION}.tgz \
  && tar -zxvf /tmp/spark.tgz -C /opt/ \
  && mv /opt/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION} /tmp/spark

FROM ${HADOOP_IMAGE}
RUN yum install -y python38
ENV SPARK_DIR=/opt/spark
COPY --from=spark-distr /tmp/spark ${SPARK_DIR}
WORKDIR ${SPARK_DIR}
