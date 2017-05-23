FROM alpine:latest

RUN apk --update add bash whois openssh openrc
WORKDIR /root

ADD authorized_keys /tmp/authorized_keys
RUN mkdir /root/.ssh/ && cat /tmp/authorized_keys >> /root/.ssh/authorized_keys && rm -f /tmp/authorized_keys


