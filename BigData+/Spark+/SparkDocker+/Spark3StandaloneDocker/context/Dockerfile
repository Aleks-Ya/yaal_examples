FROM almalinux-updated as spark-distr
ARG SPARK_VERSION
ENV HADOOP_BUILD_VERSION=3
RUN wget -O /tmp/spark.tar.gz https://archive.apache.org/dist/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION}.tgz \
  && tar -zxvf /tmp/spark.tar.gz -C /tmp/ \
  && mv /tmp/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION} /tmp/spark \
  && rm -f /tmp/spark.tar.gz


FROM almalinux-updated

RUN yum install -y java python311

ENV SPARK_HOME=/opt/spark
ENV SPARK_CONF_DIR=${SPARK_HOME}/conf
ENV PATH=$PATH:$SPARK_HOME/bin::$SPARK_HOME/sbin

COPY --from=spark-distr /tmp/spark ${SPARK_HOME}

ADD spark-defaults.conf ${SPARK_CONF_DIR}/
ADD *.sh ${SPARK_HOME}/

WORKDIR ${SPARK_HOME}
