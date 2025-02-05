set -e

source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk use java 11.0.26-zulu

sbt clean package
cp -v target/scala-2.12/spark3corestandalone_2.12-1.jar /tmp/spark-standalone-cluster-shared/spark3corestandalone.jar
