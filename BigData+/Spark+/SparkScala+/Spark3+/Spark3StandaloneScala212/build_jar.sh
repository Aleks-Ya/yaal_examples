set -e

source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk use java 11.0.30-zulu

sbt clean package
mkdir -p /tmp/spark-standalone-cluster-shared
cp -v target/scala-2.12/spark3standalonescala212_2.12-1.jar /tmp/spark-standalone-cluster-shared/spark3corestandalone.jar
