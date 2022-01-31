ARG VERSION
FROM alpine:$VERSION

RUN apk --update add bash python2 && python -m ensurepip --upgrade
RUN pip install Jinja2

WORKDIR /root
