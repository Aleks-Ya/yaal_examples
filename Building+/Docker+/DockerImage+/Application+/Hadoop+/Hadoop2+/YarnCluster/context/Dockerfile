ARG HADOOP_VERSION=2.10.1
FROM hadoop-base-image-${HADOOP_VERSION}:1
ARG HADOOP_VERSION

RUN yum install -y rsync openssh-clients openssh-server

ENV JAVA_HOME=/etc/alternatives/jre
ENV HADOOP_PREFIX=/opt/hadoop
ENV HADOOP_CONF_DIR=${HADOOP_PREFIX}/etc/hadoop
ENV HADOOP_LOG_DIR=/tmp/logs
ENV YARN_LOG_DIR=${HADOOP_LOG_DIR}/yarn
ENV PATH=$PATH:$HADOOP_PREFIX/bin:$HADOOP_PREFIX/sbin
ENV USER=$(whoami)

ADD yarn-site.xml ${HADOOP_CONF_DIR}
ADD mapred-site.xml ${HADOOP_CONF_DIR}

RUN mkdir -p ${HADOOP_LOG_DIR} \
  && sed -i "s@\${JAVA_HOME}@${JAVA_HOME}@g" ${HADOOP_PREFIX}/etc/hadoop/hadoop-env.sh

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

WORKDIR ${HADOOP_PREFIX}
