FROM almalinux-updated

ARG LIVY_VERSION
ENV LIVY_ZIP=/tmp/livy.zip
ENV LIVY_HOME=/opt/livy

RUN wget -O ${LIVY_ZIP} https://dlcdn.apache.org/incubator/livy/${LIVY_VERSION}-incubating/apache-livy-${LIVY_VERSION}-incubating-bin.zip
RUN tar -zxvf ${LIVY_ZIP} -C /opt/ \
  && mv /opt/apache-livy-${LIVY_VERSION}-incubating-bin ${LIVY_HOME} \
  && rm -f ${LIVY_ZIP}

ENV PATH=$PATH:$LIVY_HOME/bin
ENV LIVY_CONF_DIR=$LIVY_HOME/conf
ADD *.conf ${LIVY_CONF_DIR}/
ADD *.sh ${LIVY_CONF_DIR}/
ADD *.properties ${LIVY_CONF_DIR}/

WORKDIR ${LIVY_HOME}
