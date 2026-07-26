FROM alpine

RUN apk update && \
  apk add --no-cache openssl curl && \
  rm -rf /var/cache/apk/*

WORKDIR /root
CMD sh
