package sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.scalatest.{BeforeAndAfterAll, FlatSpec}
import org.scalatest.Matchers._

import scala.collection.immutable._

/**
  * Ways to instantiate DataFrame object.
  */
class CreateDfTest extends FlatSpec with BeforeAndAfterAll {

  var sc: SparkContext = _
  var sql: SQLContext = _

  override def beforeAll() {
    val conf = new SparkConf().setAppName("SqlContextTest").setMaster("local")
    sc = new SparkContext(conf)
    sql = new SQLContext(sc)
  }

  "Apply schema to RDD" should "print table" in {
    val peopleRdd = sc.parallelize(Seq("Jhon,25", "Peter,35"))
    val nameField = StructField("name", StringType, nullable = true)
    val ageField = StructField("age", IntegerType, nullable = true)
    val schema = StructType(nameField :: ageField :: Nil)
    val rowRdd = peopleRdd.map(_.split(",")).map(p => Row(p(0), p(1).toInt))
    val peopleDf = sql.createDataFrame(rowRdd, schema)
    peopleDf.registerTempTable("people")
    sql.tableNames.toList should contain("people")

    println("Tables: " + sql.tableNames.toList)

    val selectDf = sql.sql("SELECT name, age FROM people WHERE age > 30")
    selectDf.map(r => r.toString()).collect should contain("[Peter,35]")

    peopleDf.printSchema //-> peopleSchemaRdd.schema.treeString
    peopleDf.show

    println("JSON: " + peopleDf.schema.prettyJson)
    val tree = peopleDf.schema.treeString

    tree shouldEqual
      "root\n" +
        " |-- name: string (nullable = true)\n" +
        " |-- age: string (nullable = true)\n"
  }

  class People(val name: String, val age: Int) {
    override def toString: String = name + "-" + age
  }

  "From Class" should "create DF from Object and Class" in {
    val john = new People("John", 25)
    val peter = new People("Peter", 35)
    val data = java.util.Arrays.asList(john, peter)
    val df = sql.createDataFrame(data, classOf[People])
    df.show()
    //don't work
    //df.collect should contain inOrderOnly (john, peter)
  }

  override def afterAll() {
    sc.stop()
  }
}