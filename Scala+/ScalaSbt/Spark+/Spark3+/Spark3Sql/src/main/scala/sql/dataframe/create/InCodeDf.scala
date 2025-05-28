package sql.dataframe.create

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/**
 * Build: sbt package
 * Copy JAR: docker cp /home/aleks/pr/home/yaal_examples/BigData+/Spark+/Spark2+/Spark2SQL/target/scala-2.11/spark2sql_2.11-1.jar spark_master_1:/tmp/
 * Run: docker exec -it spark_master_1 spark-submit --class sql.dataframe.create.InCodeDf --master spark://master:7077 /tmp/spark2sql_2.11-1.jar
 */
object InCodeDf {

  def main(args: Array[String]): Unit = {
    println("Start")
    val ss = SparkSession.builder()
      .appName(getClass.getSimpleName).getOrCreate()

    val peopleRdd = ss.sparkContext.parallelize(Seq("Jhon,25", "Peter,35"))
    val nameField = StructField("name", StringType, nullable = true)
    val ageField = StructField("age", IntegerType, nullable = true)
    val schema = StructType(nameField :: ageField :: Nil)
    val rowRdd = peopleRdd.map(_.split(",")).map(p => Row(p(0), p(1).toInt))
    val peopleDf = ss.createDataFrame(rowRdd, schema)
    peopleDf.createOrReplaceTempView("people")
    println(peopleDf)
    assert("[name: string, age: int]".equals(peopleDf.toString()))

    println("Tables: " + ss.sqlContext.tableNames.toList)

    val selectDf = ss.sql("SELECT name, age FROM people WHERE age > 30")
    println(selectDf.collect.map(_.toString).mkString("Array(", ", ", ")"))

    peopleDf.printSchema //-> peopleSchemaRdd.schema.treeString
    peopleDf.show

    println("JSON: " + peopleDf.schema.prettyJson)
    val tree = peopleDf.schema.treeString

    println(tree)
    assert(
      ("root\n" +
        " |-- name: string (nullable = true)\n" +
        " |-- age: integer (nullable = true)\n")
        .equals(tree))

    println("Finish")
  }

}
