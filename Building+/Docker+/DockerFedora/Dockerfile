#Run Fedora in Docker

FROM fedora:25

#delete this line and the file?
COPY google-chrome.repo /etc/yum.repos.d/google-chrome.repo

#TRY TO ADD DNS TO FEDORA

#RUN yum -y update
#RUN echo 8.8.8.8 foo > /etc/hosts; cat /etc/hosts
#RUN echo 10.66.0.6 foo > /etc/hosts; cat /etc/hosts

RUN cat /etc/resolv.conf
RUN echo "nameserver 10.66.0.6" > /etc/resolv.conf
RUN echo "nameserver 8.8.8.8" > /etc/resolv.conf
#RUN systemctl restart NetworkManager
RUN service NetworkManager restart
RUN cat /etc/resolv.conf

#RUN rm -f /etc/firewalld/direct.xml
#RUN mkdir /etc/firewalld
#RUN touch /etc/firewalld/direct.xml
#RUN echo '<?xml version="1.0" encoding="utf-8"?><direct>' >> /etc/firewalld/direct.xml
#RUN echo '[ <rule ipv="ipv4" table="filter" chain="FORWARD_direct" priority="0"> -i eth0 -o eth1 -j ACCEPT </rule> ]' >> /etc/firewalld/direct.xml
#RUN echo '</direct>' >> /etc/firewalld/direct.xml
#RUN cat /etc/firewalld/direct.xml


RUN dnf upgrade -y\
	&& dnf install -y\
		chromedriver-59.0.3071.104-1.fc25\
		firefox-54.0-2.fc25\
		google-chrome-stable-59.0.3071.115-1\
		python-pip-8.1.2-2.fc25\
		xorg-x11-server-Xvfb-1.19.3-1.fc25\
	&& dnf clean all
