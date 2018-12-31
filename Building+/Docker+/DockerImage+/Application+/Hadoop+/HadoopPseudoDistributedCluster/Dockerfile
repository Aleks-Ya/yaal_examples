ARG HADOOP_VERSION=2.9.2
FROM hadoop-base-image-${HADOOP_VERSION}:1.0
ARG HADOOP_VERSION

RUN yum install -y rsync which openssh-clients openssh-server net-tools nano

ENV JAVA_HOME=/etc/alternatives/jre
ENV HADOOP_DIR=/opt/hadoop

RUN mkdir ${HADOOP_DIR}/input \
  && cp ${HADOOP_DIR}/etc/hadoop/*.xml ${HADOOP_DIR}/input \
  && sed -i "s@\${JAVA_HOME}@${JAVA_HOME}@g" ${HADOOP_DIR}/etc/hadoop/hadoop-env.sh \
  && mv ${HADOOP_DIR}/share/hadoop/mapreduce/hadoop-mapreduce-examples-${HADOOP_VERSION}.jar \
        ${HADOOP_DIR}/share/hadoop/mapreduce/hadoop-mapreduce-examples.jar

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
  && ./bin/hadoop jar ./share/hadoop/mapreduce/hadoop-mapreduce-examples.jar grep ./input ./output 'dfs[a-z.]+' \
  && bin/hdfs dfs -get output ./output \
  && cat output/*
