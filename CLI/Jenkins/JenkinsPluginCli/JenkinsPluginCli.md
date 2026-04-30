# jenkins-plugin-cli CLI

## Info
Help: `jenkins-plugin-cli --help`
Verbose: `jenkins-plugin-cli --verbose --list`

## Commands
List installed plugins: `jenkins-plugin-cli --list`
Install plugins from file `plugins.txt`: `jenkins-plugin-cli --plugin-file plugins.txt`
Install plugins from stringt: `jenkins-plugin-cli --plugins maven-plugin:latest javadoc:latest`

## Errors
### `NoHttpResponseException`
Command: `jenkins-plugin-cli --plugins maven-plugin:latest`
Error message:
```
=> => # Mar 01, 2024 3:19:59 AM org.apache.http.impl.execchain.RetryExec execute
=> => # INFO: I/O exception (org.apache.http.NoHttpResponseException) caught when processing request to {s}->https://updates.jenkins.io:443: The target server failed to respond
=> => # Mar 01, 2024 3:19:59 AM org.apache.http.impl.execchain.RetryExecexecute
=> => # INFO: Retrying request to {s}->https://updates.jenkins.io:443 
```
Solution: not found
Links:
- Issue at GitHub: https://github.com/jenkinsci/plugin-installation-manager-tool/issues/386
- Issue at Jenkis Forum: https://community.jenkins.io/t/extremly-slow-plugin-installs-using-jenkins-plugin-cli/4409
