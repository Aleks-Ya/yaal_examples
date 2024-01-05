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
Run in local mode: `spark-shell --master local[2]`
Connect to existing Master: `spark-shell --master spark://spark-standalone-cluster-master:7077`

## Test calculations in Shell
1. Test Spark Core: `sc.parallelize(Seq(1, 2, 3)).collect()`
2. Test Spark SQL:
```Scala
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
val schema = StructType(StructField("name", StringType) :: StructField("age", IntegerType) :: Nil)
val rdd = sc.parallelize(Seq(Row("Jhon", 25), Row("Peter", 35)))
val peopleDf = spark.sqlContext.createDataFrame(rdd, schema)
peopleDf.show
```
