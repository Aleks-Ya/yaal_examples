package mysql

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.scalatest.FlatSpec
import org.scalatest.Matchers.convertToStringShouldWrapper
import org.scalatest.BeforeAndAfterAll
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.rdd._

class SqlContextTest extends FlatSpec with BeforeAndAfterAll {

  var sc: SparkContext = null
  var sql: SQLContext = null

  override def beforeAll() {
    val conf = new SparkConf().setAppName("SqlContextTest").setMaster("local")
    sc = new SparkContext(conf)
    sql = new SQLContext(sc)
  }

  "Process lines from the access log" should "return Top5" in {
    val people = sc.parallelize(Seq("Jhon,25", "Peter,35"))
    val schemaStr = "name age"
    val schema = StructType(schemaStr.split(" ").map(fieldName => StructField(fieldName, StringType, true)))
    val rowRdd = people.map(_.split(",")).map(p => Row(p(0), p(1).trim))
    val peopleSchemaRdd = sql.applySchema(rowRdd, schema)
    peopleSchemaRdd.printSchema //-> peopleSchemaRdd.schema.treeString
    peopleSchemaRdd.show()

    println("JSON: " + peopleSchemaRdd.schema.prettyJson)
    val tree = peopleSchemaRdd.schema.treeString

    tree shouldEqual
      "root\n" +
      " |-- name: string (nullable = true)\n" +
      " |-- age: string (nullable = true)\n"
  }

  override def afterAll() {
    sc.stop()
  }
}