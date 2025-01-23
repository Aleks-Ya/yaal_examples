package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array, col, collect_list, flatten}
import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class FlattenFunction extends AnyFlatSpec with Matchers {
  private val df = Factory.createDf("country STRING, orders ARRAY<INT>",
    Row("USA", Seq(10, 20)),
    Row("Canada", Seq(100, 200)),
    Row("USA", Seq(30, 40)),
    Row("Canada", Seq(300, 400)))

  it should "use flatten function in select" in {
    val df = Factory.createDf("country STRING, orders ARRAY<ARRAY<INT>>",
      Row("USA", Seq(Seq(10, 20), Seq(30, 40))),
      Row("Canada", Seq(Seq(100, 200), Seq(300, 400))))
    val updatedDf = df.select(col("country"), flatten(col("orders")) as "country_orders")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"country":"USA","country_orders":[10,20,30,40]}""",
      """{"country":"Canada","country_orders":[100,200,300,400]}""")
  }

  it should "use flatten twice" in {
    val df = Factory.createDf("country STRING, orders ARRAY<ARRAY<ARRAY<INT>>>",
      Row("USA", Seq(
        Seq(Seq(11, 21), Seq(31, 41)),
        Seq(Seq(12, 22), Seq(32, 42)))),
      Row("Canada", Seq(
        Seq(Seq(101, 201), Seq(301, 401)),
        Seq(Seq(102, 202), Seq(302, 402)))))
    val updatedDf = df.select(col("country"), flatten(flatten(col("orders"))) as "country_orders")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"country":"USA","country_orders":[11,21,31,41,12,22,32,42]}""",
      """{"country":"Canada","country_orders":[101,201,301,401,102,202,302,402]}""")
  }

  it should "use flatten and extract a nested field" in {
    val personStruct = StructType.fromDDL("name STRING, age INT")
    val df = Factory.createDf(Map("country" -> StringType, "people" -> ArrayType(ArrayType(personStruct))),
      Row("USA", Seq(Seq(Row("John", 10), Row("Mary", 20)), Seq(Row("Mark", 30), Row("Erick", 40)))),
      Row("Canada", Seq(Seq(Row("Patrick", 100), Row("Buddha", 200)), Seq(Row("Petr", 300), Row("Kant", 400)))
      ))
    val updatedDf = df.select(col("country"), flatten(col("people"))("name") as "country_people")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"country":"USA","country_people":["John","Mary","Mark","Erick"]}""",
      """{"country":"Canada","country_people":["Patrick","Buddha","Petr","Kant"]}""")
  }

  it should "use flatten function in select with collect_list" in {
    val updatedDf = df.select(flatten(collect_list("orders")) as "all_orders")
    updatedDf.toJSON.collect() should contain only """{"all_orders":[10,20,100,200,30,40,300,400]}"""
  }

  it should "use flatten function in agg" in {
    val updatedDf = df.agg(flatten(collect_list("orders")) as "all_orders")
    updatedDf.toJSON.collect() should contain only """{"all_orders":[10,20,100,200,30,40,300,400]}"""
  }

  it should "fail if flatten is applied to an one-level array" in {
    val e = intercept[Exception] {
      Factory.createDf("country STRING, orders ARRAY<ARRAY<INT>>",
        Row("USA", Seq(10, 20)),
        Row("Canada", Seq(100, 200)))
    }
    e.getMessage should include("java.lang.Integer is not a valid external type for schema of array<int>")
  }

  it should "flatten of empty array" in {
    import Factory.ss.implicits._
    val df = Seq[String]("a").toDF("id")
    val updatedDf = df.select(flatten(array(array())) as "null_flatten")
    updatedDf.toJSON.collect() should contain only """{"null_flatten":[]}"""
  }

  it should "flatten array with null" in {
    val df = Factory.createDf("country STRING, orders ARRAY<ARRAY<INT>>",
      Row("USA", Seq(Seq(10, null), Seq(30, 40))),
      Row("Canada", Seq(Seq(100, 200), Seq(null, null))),
      Row("Germany", Seq(Seq(1000, 2000), null)),
      Row("France", Seq(null)),
      Row("Belgium", null)
    )
    val updatedDf = df.select(col("country"), flatten(col("orders")) as "country_orders")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"country":"USA","country_orders":[10,null,30,40]}""",
      """{"country":"Canada","country_orders":[100,200,null,null]}""",
      """{"country":"Germany","country_orders":null}""",
      """{"country":"France","country_orders":null}""",
      """{"country":"Belgium","country_orders":null}"""
    )
  }
}