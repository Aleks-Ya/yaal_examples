ARG PYTHON_VERSION
FROM python:${PYTHON_VERSION}

ARG JAVA_VERSION
RUN apt-get update \
	&& apt-get install -y "openjdk-${JAVA_VERSION}-jdk" \
	&& python -m pip install --no-cache-dir --upgrade pip \
	&& python -m pip install --no-cache-dir pyspark

CMD [ "bash" ]