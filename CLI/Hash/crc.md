# crc CLI (Code Ready Containers, OpenShift)

[Docs](https://access.redhat.com/documentation/en-us/red_hat_codeready_containers/1.27/html/getting_started_guide/using-codeready-containers_gsg)

Use: 3. Using CodeReady Containers

### CLI

Show help: `crc help`
Show version: `crc version`
Start cluster: `crc start`
Status: `crc status`
Open console in browser (password: developer/developer): `crc console`
Show admin and developer credentials: `crc console --credentials`
Login to "oc" CLI:
```
eval $(crc oc-env)
oc login -u developer https://api.crc.testing:6443
```
Stop cluster: `crc stop`
Remove cluster (release disk space): `crc delete`
