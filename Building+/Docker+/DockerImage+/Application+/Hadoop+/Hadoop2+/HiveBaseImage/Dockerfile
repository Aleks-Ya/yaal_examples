ARG HADOOP_BASE_IMAGE
FROM ${HADOOP_BASE_IMAGE}
ARG HIVE_VERSION

ENV HIVE_TGZ=/tmp/hive.tgz
ENV HIVE_DIR=/opt/hive

RUN wget -O $HIVE_TGZ https://apache.volia.net/hive/hive-${HIVE_VERSION}/apache-hive-${HIVE_VERSION}-bin.tar.gz \
  && tar -zxvf $HIVE_TGZ -C /opt/ \
  && mv /opt/apache-hive-${HIVE_VERSION}-bin ${HIVE_DIR} \
  && rm -f $HIVE_TGZ

WORKDIR ${HIVE_DIR}
