FROM alpine:latest

ENV STATUS_FILE=/tmp/ok_status
RUN touch $STATUS_FILE

HEALTHCHECK --interval=1s CMD [ -f "$STATUS_FILE" ] || exit 1

CMD sh
