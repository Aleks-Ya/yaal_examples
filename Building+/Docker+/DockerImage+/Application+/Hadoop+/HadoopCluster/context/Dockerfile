ARG HADOOP_VERSION=2.9.2
FROM spark-2.4.0-hadoop-${HADOOP_VERSION}-base-image:1.0
ARG HADOOP_VERSION

RUN yum install -y rsync which openssh-clients openssh-server net-tools nano telnet less

ENV JAVA_HOME=/etc/alternatives/jre
ENV HADOOP_PREFIX=/opt/hadoop
ENV HADOOP_HOME=${HADOOP_PREFIX}
ENV SPARK_HOME=/opt/spark
ENV HADOOP_CONF_DIR=${HADOOP_PREFIX}/etc/hadoop
ENV HADOOP_LOG_DIR=/tmp/logs
ENV HADOOP_VERSION=${HADOOP_VERSION}
ENV PATH=$PATH:$HADOOP_PREFIX/bin:$HADOOP_PREFIX/sbin:$SPARK_HOME/bin::$SPARK_HOME/sbin
ENV YARN_LOG_DIR=${HADOOP_LOG_DIR}/yarn

ADD *.xml ${HADOOP_CONF_DIR}/

RUN mkdir -p ${HADOOP_LOG_DIR} \
  && mkdir -p ${YARN_LOG_DIR} \
  && sed -i "s@\${JAVA_HOME}@${JAVA_HOME}@g" ${HADOOP_PREFIX}/etc/hadoop/hadoop-env.sh

RUN ssh-keygen -A \
  && echo 'root:screencast' | chpasswd \
  && sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config \
  && sed -i 's/#PasswordAuthentication yes/PasswordAuthentication yes/' /etc/ssh/sshd_config \
  && mkdir ~/.ssh/ \
  && ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa \
  && cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys \
  && chmod 0600 ~/.ssh/authorized_keys

ADD *.sh ${HADOOP_PREFIX}/

WORKDIR ${HADOOP_PREFIX}
