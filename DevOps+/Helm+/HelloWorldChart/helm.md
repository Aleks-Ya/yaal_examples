# Helm

Create new chart: `helm create helloworld`  
Render the template without install the chart: `helm install --debug --dry-run hworld ./helloworld`
Install the chart: `helm install hworld ./helloworld`  
Check the manifest: `helm get manifest hworld`  
Uninstall the chart: `helm uninstall hworld`  