# spark-shell CLI

## Install
### By Python
Install: `pip install pyspark`
Upgrade to latest version: `pip install pyspark -U`
### From ZIP
1. Download Spark distributive: https://spark.apache.org/downloads.html
2. Unzip
3. Choose Java 8, e.g.: `sdk use java 8.0.302-open`
4. Run `./bin/spark-shell`

## Commands
Help: `spark-shell -h`
Show version: `spark-shell --version`
Connect to existing Master: `spark-shell --master spark://spark-standalone-cluster-master:7077`
