ARG HADOOP_VERSION=2.9.2
FROM hadoop-base-image-${HADOOP_VERSION}:1.0
ARG HADOOP_VERSION

RUN yum install -y rsync which openssh-clients openssh-server net-tools nano telnet less

ENV JAVA_HOME=/etc/alternatives/jre
ENV HADOOP_PREFIX=/opt/hadoop
ENV HADOOP_CONF_DIR=${HADOOP_PREFIX}/etc/hadoop
ENV HADOOP_LOG_DIR=/tmp/logs
RUN mkdir -p ${HADOOP_LOG_DIR}

RUN mkdir ${HADOOP_PREFIX}/input \
  && cp ${HADOOP_PREFIX}/etc/hadoop/*.xml ${HADOOP_PREFIX}/input \
  && sed -i "s@\${JAVA_HOME}@${JAVA_HOME}@g" ${HADOOP_PREFIX}/etc/hadoop/hadoop-env.sh

ADD core-site.xml ${HADOOP_CONF_DIR}
ADD hdfs-site.xml ${HADOOP_CONF_DIR}
ADD mapred-site.xml ${HADOOP_CONF_DIR}
ADD yarn-site.xml ${HADOOP_CONF_DIR}

RUN ssh-keygen -A \
  && echo 'root:screencast' | chpasswd \
  && sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config \
  && sed -i 's/#PasswordAuthentication yes/PasswordAuthentication yes/' /etc/ssh/sshd_config \
  && mkdir ~/.ssh/ \
  && ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa \
  && cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys \
  && chmod 0600 ~/.ssh/authorized_keys

ADD entrypoint_master.sh ${HADOOP_PREFIX}
ADD entrypoint_slave_1.sh ${HADOOP_PREFIX}
ADD entrypoint_slave_2.sh ${HADOOP_PREFIX}
ADD run_application.sh ${HADOOP_PREFIX}

WORKDIR ${HADOOP_PREFIX}
