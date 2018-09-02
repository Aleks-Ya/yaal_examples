package dataframe.create

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

/**
  * Ways to instantiate DataFrame object.
  */
class InCodeDfTest extends FlatSpec with BeforeAndAfterAll with Matchers {

  "Apply schema to RDD" should "print table" in {
    val peopleRdd = Factory.ss.sparkContext.parallelize(Seq("Jhon,25", "Peter,35"))
    val nameField = StructField("name", StringType, nullable = true)
    val ageField = StructField("age", IntegerType, nullable = true)
    val schema = StructType(nameField :: ageField :: Nil)
    val rowRdd = peopleRdd.map(_.split(",")).map(p => Row(p(0), p(1).toInt))
    val sql = Factory.ss.sqlContext
    val peopleDf = sql.createDataFrame(rowRdd, schema)
    peopleDf.createOrReplaceTempView("people")
    sql.tableNames.toList should contain("people")

    println("Tables: " + sql.tableNames.toList)

    val selectDf = sql.sql("SELECT name, age FROM people WHERE age > 30")
    selectDf.collect.map(_.toString) should contain("[Peter,35]")

    peopleDf.printSchema //-> peopleSchemaRdd.schema.treeString
    peopleDf.show

    println("JSON: " + peopleDf.schema.prettyJson)
    val tree = peopleDf.schema.treeString

    tree shouldEqual
      "root\n" +
        " |-- name: string (nullable = true)\n" +
        " |-- age: integer (nullable = true)\n"
  }

  class People(val name: String, val age: Int) {
    override def toString: String = name + "-" + age
  }

  "From Class" should "create DF from Object and Class" in {
    val john = new People("John", 25)
    val peter = new People("Peter", 35)
    val data = java.util.Arrays.asList(john, peter)
    val df = Factory.ss.sqlContext.createDataFrame(data, classOf[People])
    df.show()
    //don't work
    //df.collect should contain inOrderOnly (john, peter)
  }
}