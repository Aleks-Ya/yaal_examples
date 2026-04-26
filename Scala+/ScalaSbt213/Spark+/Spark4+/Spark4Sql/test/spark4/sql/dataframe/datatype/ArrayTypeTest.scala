package spark4.sql.dataframe.datatype

import org.apache.spark.sql.functions.{array_max, col, flatten}
import org.apache.spark.sql.{DataFrame, Dataset, Row}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class ArrayTypeTest extends AnyFlatSpec with SparkMatchers {

  it should "create an ArrayType field (with ArrayType constructor)" in {
    val df = Factory.createDf("name STRING, orders ARRAY<INT>",
      Row("USA", Array(10, 20)),
      Row("Canada", Array(30, 40)))
    df shouldContain(
      """{"name":"USA","orders":[10,20]}""",
      """{"name":"Canada","orders":[30,40]}""")
  }

  it should "create an ArrayType field (with DataTypes class)" in {
    val df = Factory.createDf("name STRING, orders ARRAY<INT>",
      Row("USA", Array(10, 20)),
      Row("Canada", Array(30, 40)))
    df shouldContain(
      """{"name":"USA","orders":[10,20]}""",
      """{"name":"Canada","orders":[30,40]}""")
  }

  it should "use Array, List, Seq for rows" in {
    val dfArray = Factory.createDf("numbers ARRAY<INT>",
      Row(Array(10, 20)),
      Row(Array(30, 40)))
    val dfList = Factory.createDf("numbers ARRAY<INT>",
      Row(List(10, 20)),
      Row(List(30, 40)))
    val dfSeq: DataFrame = Factory.createDf("numbers ARRAY<INT>",
      Row(Seq(10, 20)),
      Row(Seq(30, 40)))

    val expDdl = "numbers ARRAY<INT>"
    dfArray shouldHaveDDL expDdl
    dfList shouldHaveDDL expDdl
    dfSeq shouldHaveDDL expDdl

    val expJson = Seq(
      """{"numbers":[10,20]}""",
      """{"numbers":[30,40]}""")
    dfArray shouldContain (expJson: _*)
    dfList shouldContain (expJson: _*)
    dfSeq shouldContain (expJson: _*)
  }

  it should "get value of ArrayType field" in {
    val df = Factory.createDf("numbers ARRAY<INT>",
      Row(Array(10, 20)),
      Row(Array(30, 40)))
    df shouldHaveDDL "numbers ARRAY<INT>"
    df shouldContain(
      """{"numbers":[10,20]}""",
      """{"numbers":[30,40]}""")
    import Factory.ss.implicits._
    val sumDs: Dataset[Int] = df.map(row => row.getSeq[Int](row.fieldIndex("numbers")).sum)
    sumDs shouldHaveDDL "value INT NOT NULL"
    sumDs shouldContain(30, 70)
  }

  it should "get 1st element of array" in {
    val df = Factory.createDf("name STRING, orders ARRAY<INT>",
      Row("USA", Array(10, 20)),
      Row("Canada", Array()))
    val updatedDf = df.withColumn("first_order", col("orders")(0))
    updatedDf shouldHaveDDL "name STRING,orders ARRAY<INT>,first_order INT"
    updatedDf shouldContain(
      """{"name":"USA","orders":[10,20],"first_order":10}""",
      """{"name":"Canada","orders":[],"first_order":null}""")
  }

  it should "create array of arrays (Int)" in {
    val df = Factory.createDf("numbers ARRAY<ARRAY<INT>>",
      Row(Array(Array(1, 2), Array(3, 4))),
      Row(Array(Array(10, 20), Array(30), Array(), Array(40))))
    df shouldContain(
      """{"numbers":[[1,2],[3,4]]}""",
      """{"numbers":[[10,20],[30],[],[40]]}""")
    import Factory.ss.implicits._
    val sumDs: Dataset[Int] = df.map(row => {
      val seqOfSeqs = row.getSeq[scala.collection.Seq[Int]](row.fieldIndex("numbers"))
      seqOfSeqs.map(_.sum).sum
    })
    sumDs shouldHaveDDL "value INT NOT NULL"
    sumDs shouldContain(10, 100)
  }

  it should "create Array in Struct" in {
    val df = Factory.createDf("name STRING, sales ARRAY<INT>",
      Row("John", Array(1, 2)),
      Row("Mary", Array(10, 20)))
    df shouldContain(
      """{"name":"John","sales":[1,2]}""",
      """{"name":"Mary","sales":[10,20]}""")
    val maxDf = df.withColumn("maxSale", array_max(col("sales")))
    maxDf shouldHaveDDL "name STRING,sales ARRAY<INT>,maxSale INT"
    maxDf shouldContain(
      """{"name":"John","sales":[1,2],"maxSale":2}""",
      """{"name":"Mary","sales":[10,20],"maxSale":20}""")
  }

  it should "create Struct(Array) nested into another Struct(Array)" in {
    val df = Factory.createDf("person STRING,products ARRAY<STRUCT<title: STRING, sales: ARRAY<INT>>>",
      Row("John", Array(Row("car", Array(1, 2)), Row("house", Array(3, 4)))),
      Row("Mary", Array(Row("butter", Array(10, 20)), Row("bread", Array(30, 40)))))
    df shouldContain(
      """{"person":"John","products":[{"title":"car","sales":[1,2]},{"title":"house","sales":[3,4]}]}""",
      """{"person":"Mary","products":[{"title":"butter","sales":[10,20]},{"title":"bread","sales":[30,40]}]}""")
    val salesDf = df.withColumn("maxSale", array_max(flatten(col("products.sales"))))
    salesDf shouldHaveDDL "person STRING,products ARRAY<STRUCT<title: STRING, sales: ARRAY<INT>>>,maxSale INT"
    salesDf shouldContain(
      """{"person":"John","products":[{"title":"car","sales":[1,2]},{"title":"house","sales":[3,4]}],"maxSale":4}""",
      """{"person":"Mary","products":[{"title":"butter","sales":[10,20]},{"title":"bread","sales":[30,40]}],"maxSale":40}""")
  }

  it should "collect values from 2-dimensional array" in {
    val df = Factory.createDf("person STRING,products ARRAY<STRUCT<title: STRING, sales: ARRAY<INT>>>",
      Row("John", Array(Row("car", Array(1, 2)), Row("house", Array(3, 4)))),
      Row("Mary", Array(Row("butter", Array(10, 20)), Row("bread", Array(30, 40)))))
    df shouldContain(
      """{"person":"John","products":[{"title":"car","sales":[1,2]},{"title":"house","sales":[3,4]}]}""",
      """{"person":"Mary","products":[{"title":"butter","sales":[10,20]},{"title":"bread","sales":[30,40]}]}""")
    val salesDf = df.select(col("person"), flatten(col("products")("sales")) as "sales")
    salesDf shouldHaveDDL "person STRING,sales ARRAY<INT>"
    salesDf shouldContain(
      """{"person":"John","sales":[1,2,3,4]}""",
      """{"person":"Mary","sales":[10,20,30,40]}""")
  }

}