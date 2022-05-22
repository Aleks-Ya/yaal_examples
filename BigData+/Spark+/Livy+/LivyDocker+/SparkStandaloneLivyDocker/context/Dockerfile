FROM almalinux-updated AS spark-distr
ARG SPARK_VERSION
ENV SPARK_TGZ=/tmp/spark.tgz
ENV HADOOP_BUILD_VERSION=3.2
RUN wget -O ${SPARK_TGZ} https://archive.apache.org/dist/spark/spark-${SPARK_VERSION}/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION}.tgz \
  && tar -zxvf ${SPARK_TGZ} -C / \
  && mv /spark-${SPARK_VERSION}-bin-hadoop${HADOOP_BUILD_VERSION} /spark-distr \
  && rm -f ${SPARK_TGZ}


FROM almalinux-updated as livy-distr
ARG LIVY_VERSION
ENV LIVY_ZIP=/tmp/livy.zip

RUN wget -O ${LIVY_ZIP} https://dlcdn.apache.org/incubator/livy/${LIVY_VERSION}-incubating/apache-livy-${LIVY_VERSION}-incubating-bin.zip \
  && unzip ${LIVY_ZIP} -d /opt/ \
  && mv /opt/apache-livy-${LIVY_VERSION}-incubating-bin /livy-distr \
  && rm -f ${LIVY_ZIP}



FROM almalinux-updated AS spark
RUN yum install -y java python38

ARG SPARK_VERSION
ENV SPARK_DIR=/opt/spark
RUN mkdir -p $SPARK_DIR
COPY --from=spark-distr /spark-distr/ ${SPARK_DIR}

ENV SPARK_HOME=/opt/spark
ENV SPARK_CONF_DIR=${SPARK_HOME}/conf
ENV PATH=$PATH:$SPARK_HOME/bin::$SPARK_HOME/sbin

ADD spark/spark-defaults.conf ${SPARK_CONF_DIR}/
ADD spark/*.sh ${SPARK_HOME}/
ADD user.sh ${SPARK_HOME}/

WORKDIR ${SPARK_HOME}



FROM spark as livy
ARG LIVY_VERSION
ENV LIVY_ZIP=/tmp/livy.zip
ENV LIVY_HOME=/opt/livy
RUN mkdir -p $LIVY_HOME
COPY --from=livy-distr /livy-distr/ ${LIVY_HOME}

ENV PATH=$PATH:$LIVY_HOME/bin
ENV LIVY_CONF_DIR=${LIVY_HOME}/conf
ADD livy/conf/*.* ${LIVY_CONF_DIR}/
ADD livy/entrypoint.sh ${LIVY_HOME}/
ADD user.sh ${LIVY_HOME}/

WORKDIR ${LIVY_HOME}
