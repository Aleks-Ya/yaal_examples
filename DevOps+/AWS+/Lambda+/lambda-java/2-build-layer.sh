#!/bin/bash
set -eo pipefail
gradle -q packageLibs
mv build/distributions/lambda-java.zip build/lambda-java-lib.zip