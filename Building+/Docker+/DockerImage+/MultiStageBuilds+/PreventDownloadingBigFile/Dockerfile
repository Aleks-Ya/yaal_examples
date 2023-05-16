FROM almalinux-updated AS derby-distr
ENV DERBY_VERSION=10.14.2.0
RUN wget -O /tmp/derby.tar.gz https://archive.apache.org/dist/db/derby/db-derby-${DERBY_VERSION}/db-derby-${DERBY_VERSION}-bin.tar.gz \
  && tar -zxvf /tmp/derby.tar.gz -C /tmp/ \
  && mv /tmp/db-derby-${DERBY_VERSION}-bin /tmp/derby \
  && rm -f /tmp/derby.tar.gz

FROM almalinux
RUN yum install -y java
COPY --from=derby-distr /tmp/derby /opt/derby
CMD /opt/derby/bin/sysinfo
