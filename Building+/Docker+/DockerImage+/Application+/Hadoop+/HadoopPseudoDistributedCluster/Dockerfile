FROM centos:7

LABEL description="Setup a Hadoop Single Node Cluster in Local Mode and \
run a standard example application."

RUN yum install -y java rsync wget tar which \
  openssh-clients openssh-server net-tools nano

ENV JAVA_HOME=/etc/alternatives/jre
ENV HADOOP_DIR=/opt/hadoop
ENV HADOOP_VERSION=2.9.2

RUN wget -O /tmp/hadoop.tar.gz http://mirror.linux-ia64.org/apache/hadoop/common/hadoop-${HADOOP_VERSION}/hadoop-${HADOOP_VERSION}.tar.gz \
  && tar -zxvf /tmp/hadoop.tar.gz -C /opt/ \
  && mv /opt/hadoop-${HADOOP_VERSION} /opt/hadoop \
  && rm -f /tmp/hadoop.tar.gz

RUN mkdir ${HADOOP_DIR}/input \
  && cp ${HADOOP_DIR}/etc/hadoop/*.xml ${HADOOP_DIR}/input \
  && sed -i "s@\${JAVA_HOME}@${JAVA_HOME}@g" ${HADOOP_DIR}/etc/hadoop/hadoop-env.sh

ADD core-site.xml ${HADOOP_DIR}/etc/hadoop/
ADD hdfs-site.xml ${HADOOP_DIR}/etc/hadoop/

RUN ssh-keygen -A \
  && echo 'root:screencast' | chpasswd \
  && sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config \
  && sed -i 's/#PasswordAuthentication yes/PasswordAuthentication yes/' /etc/ssh/sshd_config

WORKDIR ${HADOOP_DIR}

CMD /usr/sbin/sshd \
  && mkdir ~/.ssh/ \
  && ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa \
  && cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys \
  && chmod 0600 ~/.ssh/authorized_keys \
  && ssh -o StrictHostKeyChecking=no localhost cat /dev/null \
  && ssh -o StrictHostKeyChecking=no 0.0.0.0 cat /dev/null \
  && bin/hdfs namenode -format \
  && ./sbin/start-dfs.sh \
  && bin/hdfs dfs -mkdir -p /user/$USER \
  && bin/hdfs dfs -mkdir -p input \
  && bin/hdfs dfs -put ./etc/hadoop/* input/ \
  && ./bin/hadoop jar ./share/hadoop/mapreduce/hadoop-mapreduce-examples-${HADOOP_VERSION}.jar grep ./input ./output 'dfs[a-z.]+' \
  && bin/hdfs dfs -get output ./output \
  && cat output/*
