export JENKINS_VERSION=$(cat version.txt)
docker run --rm \
  -p 8080:8080 -p 50000:50000 \
  --net bridge \
  --name jenkins2 \
  -v jenkins_home:/var/jenkins_home \
  --env JAVA_OPTS=-Djenkins.install.runSetupWizard=false \
  "aleks3490/jenkins:${JENKINS_VERSION}"
