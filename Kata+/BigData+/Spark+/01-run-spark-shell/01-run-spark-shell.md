# 01-run-spark-shell

## Task
Run Hello World example in Spark Shell.

## Steps
1. Install Spark Shell: `CLI/Spark/spark-shell.md`
2. Run spark shell: `spark-shell`
3. Execute a Spark Core application (RDD): `sc.parallelize(Seq(1, 2, 3)).sum()`
4. Execute a Spark SQL application (DataFrame):
	```scala
	import org.apache.spark.sql.Row
	import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
	val schema = StructType(StructField("name", StringType) :: StructField("age", IntegerType) :: Nil)
	val rdd = sc.parallelize(Seq(Row("Jhon", 25), Row("Peter", 35)))
	val df = spark.sqlContext.createDataFrame(rdd, schema)
	df.show
	```

## Cleanup
1. Stop the Spark Shell: `:quit`

## History
- 2026-02-13 success
- 2026-03-18 success
