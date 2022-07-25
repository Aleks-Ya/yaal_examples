# HelloWorldChart

Create new chart: `helm create helloworld`
Render the template without install the chart: `helm install --debug --dry-run hworld ./helloworld`
Install the chart: `helm install hworld ./helloworld`
Check the manifest: `helm get manifest hworld`
Uninstall the chart: `helm uninstall hworld`
Check the chart for problems: `helm lint helloworld`

## Package
Create a package from a chart dir: `helm package helloworld`
Push a package to a Helm repo: `curl --data-binary "@helloworld-0.1.0.tgz" http://localhost:8080/api/charts`