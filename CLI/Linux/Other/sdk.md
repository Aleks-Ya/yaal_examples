# SdkMan CLI

## Install
```shell
sudo apt install -y curl
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk version
```

## Commands
Help: `sdk help`
Help for command: `sdk help install`
Update SdkMan: `sdk selfupdate`

List actives candidates: `sdk current`
List installed candiates: `sdk list java | grep -i installed`
List available versions for Java: `sdk list java`
Choose version for current shell: `sdk use java 8.0.302-open`
Choose version for all shells: `sdk default java 8.0.302-open`
Show path to given version: `sdk home groovy 2.4.21`
Remove temp data: `sdk flush`

## Install apps
### Java
Check latest versions: `sdk list java | grep zulu`
Install:
```shell
sdk install java 8.0.402-zulu && \
sdk install java 11.0.22-zulu && \
sdk install java 17.0.10-zulu && \
sdk install java 21.0.2-zulu && \
sdk install java 21.0.2.fx-zulu
```

### Other apps
```shell
sdk install groovy && \
sdk install scala && \
sdk install kotlin && \
sdk install maven && \
sdk install ant && \
sdk install gradle && \
sdk install sbt && \
sdk install visualvm
```
