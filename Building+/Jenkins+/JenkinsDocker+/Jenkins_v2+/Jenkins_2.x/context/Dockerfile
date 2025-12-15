ARG JENKINS_VERSION=lts
FROM jenkins/jenkins:$JENKINS_VERSION

COPY plugins.txt /tmp/plugins.txt
RUN jenkins-plugin-cli --verbose --plugin-file /tmp/plugins.txt
