# sasl-sample-server CLI

Install: `sudo apt install -y sasl2-bin`

## Server
Help: `sasl-sample-server -h`

## Client
Help: `sasl-sample-client -h`





```
sasl-sample-server -i local=127.0.0.1,remote=127.0.0.1 -m ANONYMOUS
sasl-sample-server -i local=127.0.0.1;1789,remote=127.0.0.1;1789 -m ANONYMOUS

sasl2-sample-client -p 1789  -m ANONYMOUS localhost
sasl-sample-client -i local=127.0.0.1,remote=127.0.0.1 -m ANONYMOUS 
sasl-sample-client -i local=127.0.0.1;1789,remote=127.0.0.1;1789 -m ANONYMOUS -n 127.0.0.1
```