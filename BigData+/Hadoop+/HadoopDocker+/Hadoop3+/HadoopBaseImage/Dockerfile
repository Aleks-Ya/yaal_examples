FROM almalinux-updated as hadoop-distr
ARG HADOOP_VERSION
ENV HADOOP_VERSION=${HADOOP_VERSION}
RUN wget -O /tmp/hadoop.tar.gz http://apache-mirror.rbc.ru/pub/apache/hadoop/common/hadoop-${HADOOP_VERSION}/hadoop-${HADOOP_VERSION}.tar.gz \
  && tar -zxvf /tmp/hadoop.tar.gz -C /tmp/ \
  && mv /tmp/hadoop-${HADOOP_VERSION} /tmp/hadoop

FROM almalinux-updated
RUN yum install -y java
ENV JAVA_HOME=/etc/alternatives/jre
ENV HADOOP_HOME=/opt/hadoop
COPY --from=hadoop-distr /tmp/hadoop ${HADOOP_HOME}
WORKDIR ${HADOOP_HOME}
