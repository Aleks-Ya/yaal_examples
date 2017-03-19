package dataframe

import org.apache.spark.SparkContext
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.scalatest.Matchers._
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

class VisualizeDfTest extends FlatSpec with BeforeAndAfterAll {

  var ss: SparkSession = _
  var sc: SparkContext = _
  var sql: SQLContext = _
  var df: org.apache.spark.sql.DataFrame = _

  override def beforeAll() {
    ss = SparkSession.builder().master("local").getOrCreate()
    sc = ss.sparkContext
    sql = ss.sqlContext

    val peopleRdd = sc.parallelize(Seq("Jhon,25", "Peter,35"))
    val schemaStr = "name age"
    val schema = StructType(schemaStr.split(" ").map(fieldName => StructField(fieldName, StringType, nullable = true)))
    val rowRdd = peopleRdd.map(_.split(",")).map(p => Row(p(0), p(1).trim))
    df = sql.createDataFrame(rowRdd, schema)
    df.createOrReplaceTempView("people")
  }

  "Visualize DF" should "print some info" in {
    println("Table names:\n" + sql.tableNames.toList)

    println("Print Schema:")
    df.printSchema //-> peopleSchemaRdd.schema.treeString
    println("Show:")
    df.show

    println("Pretty JSON:\n" + df.schema.prettyJson)
    val tree = df.schema.treeString

    tree shouldEqual
      "root\n" +
        " |-- name: string (nullable = true)\n" +
        " |-- age: string (nullable = true)\n"

    println("Explain:\n")
    df.explain()

    println("\nExtended explain:\n")
    df.explain(extended = true)
  }

  override def afterAll() {
    sc.stop()
  }
}