FROM almalinux-updated AS common
RUN mkdir /conf
RUN echo "host=abc.com" > /conf/config.properties
CMD cat /conf/config.properties

FROM common AS server
RUN echo "name=server" >> /conf/config.properties

FROM common AS client
RUN echo "name=client" >> /conf/config.properties
