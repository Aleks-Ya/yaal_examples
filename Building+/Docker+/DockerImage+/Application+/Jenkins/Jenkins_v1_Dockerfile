FROM jenkins:1.651.3

USER root

COPY plugins.txt /usr/share/jenkins/plugins.txt

RUN /usr/local/bin/install-plugins.sh job-dsl git ssh-credentials maven credentials-binding scm-api cloudbees-folder cloudbees-credentials

RUN /usr/local/bin/install-plugins.sh workflow-aggregator
