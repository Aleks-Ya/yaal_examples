# wget CLI

Help: `wget -h`
Debug logs: `wget -d http://10.3.50.157:8080/emiss-etl-dag.zip`

Download file from Nexus with basic authentication:
```
wget --http-user=<user> --http-passwd=<pass>  http://10.3.50.157:8080/emiss-etl-dag.zip
```
Download to specific file (result is /tmp/output_file.zip):
```
wget -O /tmp/output_file.zip http://10.3.50.157:8080/emiss-etl-dag.zip
```
Download to specific directory (result is /tmp/emiss-etl-dag.zip):
```
wget -P /tmp http://10.3.50.157:8080/emiss-etl-dag.zip
```
Download file content to console:
```
wget -O - http://10.3.50.157:8080/emiss-etl-dag.zip
```
Not verbose (hide progress indicator):
```
wget -nv http://10.3.50.157:8080/emiss-etl-dag.zip
```
Send POST request:
`wget --post-data '{"tenantId":"249355261054976","needValidation":false}' http://localhost:80/jwt-validation/validate`