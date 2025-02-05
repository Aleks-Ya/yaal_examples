ARG SPARK_VERSION
FROM almalinux-updated AS spark-distr
ARG SCALA_BINARY_VERSION
ARG SPARK_VERSION
ARG HADOOP_BUILD_VERSION
RUN wget -O /tmp/spark.tar.gz https://archive.apache.org/dist/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION}.tgz \
  && tar -zxvf /tmp/spark.tar.gz -C /tmp/ \
  && mv /tmp/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION} /tmp/spark \
  && rm -f /tmp/spark.tar.gz
RUN wget -O /tmp/spark-connect.jar https://repo1.maven.org/maven2/org/apache/spark/spark-connect_${SCALA_BINARY_VERSION}/${SPARK_VERSION}/spark-connect_${SCALA_BINARY_VERSION}-${SPARK_VERSION}.jar

FROM almalinux-updated
ARG SPARK_VERSION

RUN yum install -y java-11-openjdk.x86_64 python312

ENV SPARK_VERSION=$SPARK_VERSION
ENV SPARK_HOME=/opt/spark
ENV SPARK_CONF_DIR=$SPARK_HOME/conf
ENV PATH=$PATH:$SPARK_HOME/bin::$SPARK_HOME/sbin

COPY --from=spark-distr /tmp/spark ${SPARK_HOME}
COPY --from=spark-distr /tmp/spark-connect.jar ${SPARK_HOME}

ADD spark-defaults.conf $SPARK_CONF_DIR/
ADD *.sh $SPARK_HOME/

WORKDIR $SPARK_HOME
