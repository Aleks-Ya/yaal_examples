package spark3.sql.dataframe.datatype

import org.apache.spark.SparkException
import org.apache.spark.sql.Row
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

/**
 * Handle nullable column of a primitive type.
 */
class NullablePrimitiveTest extends AnyFlatSpec with SparkMatchers {

  it should "use Row#getAs() for replacing nulls in a primitive column" in {
    import Factory.ss.implicits._
    val df = Factory.createDf("name STRING,orders INTEGER",
        Row("USA", 10), Row("Canada", 20), Row("England", null))
      .map(row => {
        val name = row.getString(row.fieldIndex("name"))
        val orders = row.getAs[Int](row.fieldIndex("orders"))
        (name, orders)
      }).toDF("name", "orders")
    df shouldHaveDDL "name STRING,orders INT NOT NULL"
    df shouldContain(
      """{"name":"USA","orders":10}""",
      """{"name":"Canada","orders":20}""",
      """{"name":"England","orders":0}"""
    )
  }

  it should "use Row#get() for replacing nulls in a primitive column" in {
    import Factory.ss.implicits._
    val df = Factory.createDf("name STRING,orders INTEGER",
        Row("USA", 10), Row("Canada", 20), Row("England", null))
      .map(row => {
        val name = row.getString(row.fieldIndex("name"))
        val orders = row.get(row.fieldIndex("orders"))
        val newOrders = if (orders == null) 0 else orders.toString.toInt
        (name, newOrders)
      }).toDF("name", "orders")
    df shouldHaveDDL "name STRING,orders INT NOT NULL"
    df shouldContain(
      """{"name":"USA","orders":10}""",
      """{"name":"Canada","orders":20}""",
      """{"name":"England","orders":0}"""
    )
  }

  it should "throw NPE on nulls in a primitive column" in {
    val e = the[SparkException] thrownBy {
      import Factory.ss.implicits._
      val df = Factory.createDf("name STRING,orders INTEGER",
          Row("USA", 10), Row("Canada", 20), Row("England", null))
        .map(row => {
          val name = row.getString(row.fieldIndex("name"))
          val orders = row.getInt(row.fieldIndex("orders"))
          (name, orders)
        })
      df shouldHaveDDL "_1 STRING,_2 INT NOT NULL"
      df.show()
    }
    e.getMessage should include("Job aborted due to stage failure")
    e.getCause shouldBe a[SparkException]
    e.getCause.getMessage shouldBe "Value at index 1 is null."
  }

}