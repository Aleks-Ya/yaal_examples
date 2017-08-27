FROM ppodgorsek/robot-framework:3.0.4

RUN whoami
RUN dnf clean all
RUN dnf update -y
RUN dnf -y install python-pip
RUN pip install -U requests
RUN pip install -U robotframework-requests
