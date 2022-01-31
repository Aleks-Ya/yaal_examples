ARG VERSION
FROM alpine:$VERSION

RUN apk --update add bash python3 py3-pip
RUN pip3 install Jinja2

WORKDIR /root
