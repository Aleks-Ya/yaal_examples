FROM python:3.6.5-alpine3.6

RUN apk --update add bash nano

ARG working_dir=/root

ADD requirements.txt ${working_dir}/
RUN pip install --no-cache-dir -r ${working_dir}/requirements.txt

ARG python_path=${working_dir}/pythonpath
ADD pycharm-debug-py3k.egg ${python_path}/
ENV PYTHONPATH ${python_path}
EXPOSE 40100

ARG script_dir=${working_dir}/scripts

WORKDIR ${working_dir}

CMD [ "bash" ]
