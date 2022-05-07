package dataframe.datatype

import factory.Factory
import org.apache.spark.SparkException
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Handle nullable column of a primitive type.
 */
class NullablePrimitive extends AnyFlatSpec with Matchers {

  it should "use Row#getAs() for replacing nulls in a primitive column" in {
    import Factory.ss.sqlContext.implicits._
    val df = Factory.createDf(Map("name" -> StringType, "orders" -> IntegerType),
      Row("USA", 10), Row("Canada", 20), Row("England", null))
      .map(row => {
        val name = row.getString(row.fieldIndex("name"))
        val orders = row.getAs[Int](row.fieldIndex("orders"))
        (name, orders)
      }).toDF("name", "orders")
    df.show()
    df.printSchema()
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"USA","orders":10}""",
      """{"name":"Canada","orders":20}""",
      """{"name":"England","orders":0}"""
    )
  }

  it should "use Row#get() for replacing nulls in a primitive column" in {
    import Factory.ss.sqlContext.implicits._
    val df = Factory.createDf(Map("name" -> StringType, "orders" -> IntegerType),
      Row("USA", 10), Row("Canada", 20), Row("England", null))
      .map(row => {
        val name = row.getString(row.fieldIndex("name"))
        val orders = row.get(row.fieldIndex("orders"))
        val newOrders = if (orders == null) 0 else orders.toString.toInt
        (name, newOrders)
      }).toDF("name", "orders")
    df.show()
    df.printSchema()
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"USA","orders":10}""",
      """{"name":"Canada","orders":20}""",
      """{"name":"England","orders":0}"""
    )
  }

  it should "throw NPE on nulls in a primitive column" in {
    val e = the[SparkException] thrownBy {
      import Factory.ss.sqlContext.implicits._
      Factory.createDf(Map("name" -> StringType, "orders" -> IntegerType),
        Row("USA", 10), Row("Canada", 20), Row("England", null))
        .map(row => {
          val name = row.getString(row.fieldIndex("name"))
          val orders = row.getInt(row.fieldIndex("orders"))
          (name, orders)
        })
        .show()
    }
    e.getMessage should include("Job aborted due to stage failure")
    e.getCause shouldBe a[NullPointerException]
  }

}