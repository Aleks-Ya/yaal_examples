FROM alpine:latest

RUN if ping -c 1 8.8.8.8 &> /dev/null; then echo OK; else exit 1; fi
RUN if ping -c 1 10.66.0.6 &> /dev/null; then echo OK; else exit 1; fi
RUN if ping -c 1 8.8.4.4 &> /dev/null; then echo OK; else exit 1; fi

CMD echo "Hello"
