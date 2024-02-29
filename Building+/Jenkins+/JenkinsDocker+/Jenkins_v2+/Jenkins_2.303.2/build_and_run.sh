export JENKINS_VERSION=$1
docker build --build-arg JENKINS_VERSION=${JENKINS_VERSION} -t "aleks3490/jenkins:${JENKINS_VERSION}" context
docker run --rm -p 8080:8080 -p 50000:50000 --net bridge --name jenkins2 \
  --env JAVA_OPTS=-Djenkins.install.runSetupWizard=false "aleks3490/jenkins:${JENKINS_VERSION}"
