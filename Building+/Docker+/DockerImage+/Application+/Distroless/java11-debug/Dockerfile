FROM gcr.io/distroless/java11-debian11:debug
COPY jlhttp-2.6.jar /opt/jlhttp.jar
COPY index.html /opt/index.html
CMD [ "/opt/jlhttp.jar", "/opt", "8086"]
