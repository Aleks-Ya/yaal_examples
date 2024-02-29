ARG JENKINS_VERSION
FROM jenkins/jenkins:$JENKINS_VERSION

COPY plugins.txt /tmp/plugins.txt
RUN /usr/local/bin/install-plugins.sh $(cat /tmp/plugins.txt | tr '\n' ' ')
