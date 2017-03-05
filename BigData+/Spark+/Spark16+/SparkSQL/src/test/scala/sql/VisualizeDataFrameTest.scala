package sql

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.scalatest.FlatSpec
import org.scalatest.Matchers._
import org.scalatest.BeforeAndAfterAll
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.rdd._

class VisualizeDataFrameTest extends FlatSpec with BeforeAndAfterAll {

  var sc: SparkContext = null
  var sql: SQLContext = null
  var df: DataFrame = null

  override def beforeAll() {
    val conf = new SparkConf().setAppName("SqlContextTest").setMaster("local")
    sc = new SparkContext(conf)
    sql = new SQLContext(sc)

    val peopleRdd = sc.parallelize(Seq("Jhon,25", "Peter,35"))
    val schemaStr = "name age"
    val schema = StructType(schemaStr.split(" ").map(fieldName => StructField(fieldName, StringType, true)))
    val rowRdd = peopleRdd.map(_.split(",")).map(p => Row(p(0), p(1).trim))
    df = sql.createDataFrame(rowRdd, schema)
    df.registerTempTable("people")
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