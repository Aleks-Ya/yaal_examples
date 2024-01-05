# 01-run-spark-shell

## Task
Run Hello World example in Spark Shell.

## Setup
1. Download Spark binaries from https://spark.apache.org/downloads.html
2. Unpack `spark-3.5.0-bin-hadoop3.tgz` to `~/installed` directory.
3. Change current directory to `~/installed/spark-3.5.0-bin-hadoop3/bin`
4. Run spark shell: `spark-shell`
5. Execute a Spark Core application (RDD): `sc.parallelize(Seq(1, 2, 3)).sum()`
6. Execute a Spark SQL application (DataFrame):
```Scala
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
val schema = StructType(StructField("name", StringType) :: StructField("age", IntegerType) :: Nil)
val rdd = sc.parallelize(Seq(Row("Jhon", 25), Row("Peter", 35)))
val peopleDf = spark.sqlContext.createDataFrame(rdd, schema)
peopleDf.show
```

## Cleanup
1. Delete `~/installed/spark-3.5.0-bin-hadoop3` directory
