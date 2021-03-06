ARG HADOOP_VERSION=2.9.2
FROM hadoop-base-image-${HADOOP_VERSION}:1.0
ARG HADOOP_VERSION

RUN yum install -y rsync which openssh-clients openssh-server net-tools nano

ENV JAVA_HOME=/etc/alternatives/jre
ENV HADOOP_PREFIX=/opt/hadoop

RUN mkdir ${HADOOP_PREFIX}/input \
  && cp ${HADOOP_PREFIX}/etc/hadoop/*.xml ${HADOOP_PREFIX}/input \
  && sed -i "s@\${JAVA_HOME}@${JAVA_HOME}@g" ${HADOOP_PREFIX}/etc/hadoop/hadoop-env.sh \
  && mv ${HADOOP_PREFIX}/share/hadoop/mapreduce/hadoop-mapreduce-examples-${HADOOP_VERSION}.jar \
        ${HADOOP_PREFIX}/share/hadoop/mapreduce/hadoop-mapreduce-examples.jar

ADD core-site.xml ${HADOOP_PREFIX}/etc/hadoop/
ADD hdfs-site.xml ${HADOOP_PREFIX}/etc/hadoop/
ADD mapred-site.xml ${HADOOP_PREFIX}/etc/hadoop/
ADD yarn-site.xml ${HADOOP_PREFIX}/etc/hadoop/

RUN ssh-keygen -A \
  && echo 'root:screencast' | chpasswd \
  && sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config \
  && sed -i 's/#PasswordAuthentication yes/PasswordAuthentication yes/' /etc/ssh/sshd_config \
  && mkdir ~/.ssh/ \
  && ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa \
  && cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys \
  && chmod 0600 ~/.ssh/authorized_keys

ADD entrypoint.sh ${HADOOP_PREFIX}
ADD run_application.sh ${HADOOP_PREFIX}

WORKDIR ${HADOOP_PREFIX}

EXPOSE 8088

ENTRYPOINT ["./entrypoint.sh"]
