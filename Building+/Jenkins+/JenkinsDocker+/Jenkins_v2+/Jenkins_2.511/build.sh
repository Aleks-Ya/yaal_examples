export JENKINS_VERSION=$1
docker build --build-arg JENKINS_VERSION=${JENKINS_VERSION} -t "aleks3490/jenkins:${JENKINS_VERSION}" context
