ARG IMAGE

FROM almalinux AS people
RUN echo "John" > /people.txt

FROM ${IMAGE}
RUN echo "London" > cities.txt
COPY --from=people /people.txt people.txt 
CMD cat cities.txt && cat people.txt && cat /etc/os-release
