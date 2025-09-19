# wget CLI

## Commands
### Info
Help: `wget -h`
Debug logs: `wget -d http://10.3.50.157:8080/data.zip`
Not verbose (hide progress indicator): `wget -nv http://10.3.50.157:8080/data.zip`

### Download
Download to specific file (result is /tmp/output_file.zip): `wget -O /tmp/out.zip http://10.3.50.157:8080/data.zip`
Download to specific directory (result is /tmp/emiss-etl-dag.zip): `wget -P /tmp http://10.3.50.157:8080/data.zip`
Download file content to console: `wget -O - http://10.3.50.157:8080/data.zip`
Download file from Nexus with basic authentication: `wget --http-user=<user> --http-passwd=<pass>  http://10.3.50.157:8080/data.zip`
Create absent directories to the output file: `wget -x -O /tmp/sub1/sub2/output.zip http://10.3.50.157:8080/data.zip`

### POST
Send POST request: `wget --post-data '{"idd":"249355261054976","validation":false}' http://localhost:80/jwt-validation/validate`

Print response body to console: `wget -qO- http://httpbin.io/uuid`
