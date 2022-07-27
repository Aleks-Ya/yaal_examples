# Create Secret

## Create secret from YAML
1. Create: 
	1. Enocode username to Base64: `echo -n 'my_user' | base64` -> `bXlfdXNlcg==`
	1. Enocode password to Base64: `echo -n 'my_password' | base64` -> `bXlfcGFzc3dvcmQ=`
	1. Put encoded username and password into `secret.yaml`
	1. Create secret: `kubectl create -f secret.yaml`
1. Describe: `kubectl describe secret mysecret`
1. Delete: `kubectl delete -f secret.yaml`

## Create secret from text files
1. Create: `kubectl create secret generic mysecret2 --from-file=./username.txt --from-file=./password.txt`
1. Describe: `kubectl describe secret mysecret2`
1. Delete: `kubectl delete secret mysecret2`