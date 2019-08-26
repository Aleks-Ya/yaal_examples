FROM python:3.6.5-alpine3.6

RUN apk --update add bash

WORKDIR /usr/src/app

COPY requirements.txt ./
RUN pip install --no-cache-dir -r requirements.txt

COPY script.py ./

CMD [ "python", "script.py" ]
