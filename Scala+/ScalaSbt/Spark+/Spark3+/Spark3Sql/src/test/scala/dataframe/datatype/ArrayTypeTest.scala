package dataframe.datatype

import factory.Factory
import factory.Factory.ss
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{array_max, col, flatten}
import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.JavaConverters._

class ArrayTypeTest extends AnyFlatSpec with Matchers {

  it should "create an ArrayType field (with ArrayType constructor)" in {
    val df = Factory.createDf(Map("name" -> StringType, "orders" -> ArrayType(IntegerType)),
      Row("USA", Array(10, 20)), Row("Canada", Array(30, 40)))
    df.schema.simpleString shouldEqual "struct<name:string,orders:array<int>>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"USA","orders":[10,20]}""",
      """{"name":"Canada","orders":[30,40]}""")
  }

  it should "create an ArrayType field (with DataTypes class)" in {
    val df = Factory.createDf(Map("name" -> StringType, "orders" -> DataTypes.createArrayType(IntegerType)),
      Row("USA", Array(10, 20)), Row("Canada", Array(30, 40)))
    df.schema.simpleString shouldEqual "struct<name:string,orders:array<int>>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"USA","orders":[10,20]}""",
      """{"name":"Canada","orders":[30,40]}""")
  }

  it should "use Array, List, Seq for rows" in {
    val dfArray = Factory.createDf(Map("numbers" -> ArrayType(IntegerType)), Row(Array(10, 20)), Row(Array(30, 40)))
    val dfList = Factory.createDf(Map("numbers" -> ArrayType(IntegerType)), Row(List(10, 20)), Row(List(30, 40)))
    val dfSeq = Factory.createDf(Map("numbers" -> ArrayType(IntegerType)), Row(Seq(10, 20)), Row(Seq(30, 40)))

    val expJson = Seq("""{"numbers":[10,20]}""", """{"numbers":[30,40]}""")
    dfArray.toJSON.collect() should contain theSameElementsInOrderAs expJson
    dfList.toJSON.collect() should contain theSameElementsInOrderAs expJson
    dfSeq.toJSON.collect() should contain theSameElementsInOrderAs expJson
    dfArray.schema shouldBe dfList.schema
    dfArray.schema shouldBe dfSeq.schema
  }

  it should "get value of ArrayType field" in {
    val df = Factory.createDf(Map("numbers" -> ArrayType(IntegerType)), Row(Array(10, 20)), Row(Array(30, 40)))
    df.schema.simpleString shouldEqual "struct<numbers:array<int>>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"numbers":[10,20]}""",
      """{"numbers":[30,40]}""")
    import Factory.ss.implicits._
    val sumDf = df.map(row => row.getSeq[Int](row.fieldIndex("numbers")).sum)
    sumDf.schema.simpleString shouldEqual "struct<value:int>"
    sumDf.toJSON.collect() should contain inOrderOnly(
      """{"value":30}""",
      """{"value":70}""")
  }

  it should "get 1st element of array" in {
    val df = Factory.createDf(Map("name" -> StringType, "orders" -> ArrayType(IntegerType)),
      Row("USA", Array(10, 20)), Row("Canada", Array()))
    df.schema.simpleString shouldEqual "struct<name:string,orders:array<int>>"
    val df2 = df.withColumn("first_order", col("orders")(0))
    df2.schema.simpleString shouldEqual "struct<name:string,orders:array<int>,first_order:int>"
    df2.toJSON.collect() should contain inOrderOnly(
      """{"name":"USA","orders":[10,20],"first_order":10}""",
      """{"name":"Canada","orders":[],"first_order":null}""")
  }

  it should "create array of arrays (Int)" in {
    val fields = Map("numbers" -> ArrayType(ArrayType(IntegerType)))
    val df = Factory.createDf(fields,
      Row(Array(Array(1, 2), Array(3, 4))),
      Row(Array(Array(10, 20), Array(30), Array(), Array(40)))
    )
    df.schema.simpleString shouldEqual "struct<numbers:array<array<int>>>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"numbers":[[1,2],[3,4]]}""",
      """{"numbers":[[10,20],[30],[],[40]]}""")
    import Factory.ss.implicits._
    val sumDf = df.map(row => {
      val seqOfSeqs = row.getSeq[Seq[Int]](row.fieldIndex("numbers"))
      seqOfSeqs.map(_.sum).sum
    })
    sumDf.schema.simpleString shouldEqual "struct<value:int>"
    sumDf.toJSON.collect() should contain inOrderOnly(
      """{"value":10}""",
      """{"value":100}""")
  }

  it should "create Array in Struct" in {
    val schema = StructType(Seq(
      StructField("name", StringType),
      StructField("sales", ArrayType(IntegerType))))
    val df = Factory.createDf(schema,
      Row("John", Array(1, 2)),
      Row("Mary", Array(10, 20)))
    df.schema.simpleString shouldEqual "struct<name:string,sales:array<int>>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","sales":[1,2]}""",
      """{"name":"Mary","sales":[10,20]}""")
    val sumDf = df.withColumn("maxSale", array_max(col("sales")))
    sumDf.schema.simpleString shouldEqual "struct<name:string,sales:array<int>,maxSale:int>"
    sumDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","sales":[1,2],"maxSale":2}""",
      """{"name":"Mary","sales":[10,20],"maxSale":20}""")
  }

  it should "create Struct(Array) nested into another Struct(Array)" in {
    val schema = StructType(Seq(
      StructField("person", StringType),
      StructField("products", ArrayType(StructType(Seq(
        StructField("title", StringType),
        StructField("sales", ArrayType(IntegerType))
      ))))))
    val rows = Seq(
      Row("John", Array(Row("car", Array(1, 2)), Row("house", Array(3, 4)))),
      Row("Mary", Array(Row("butter", Array(10, 20)), Row("bread", Array(30, 40))))
    ).asJava
    val df = ss.createDataFrame(rows, schema)
    df.schema.simpleString shouldEqual "struct<person:string,products:array<struct<title:string,sales:array<int>>>>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"person":"John","products":[{"title":"car","sales":[1,2]},{"title":"house","sales":[3,4]}]}""",
      """{"person":"Mary","products":[{"title":"butter","sales":[10,20]},{"title":"bread","sales":[30,40]}]}""")
    val sumDf = df.withColumn("maxSale", array_max(flatten(col("products.sales"))))
    sumDf.schema.simpleString shouldEqual "struct<person:string,products:array<struct<title:string,sales:array<int>>>,maxSale:int>"
    sumDf.toJSON.collect() should contain inOrderOnly(
      """{"person":"John","products":[{"title":"car","sales":[1,2]},{"title":"house","sales":[3,4]}],"maxSale":4}""",
      """{"person":"Mary","products":[{"title":"butter","sales":[10,20]},{"title":"bread","sales":[30,40]}],"maxSale":40}""")
  }

  it should "collect values from 2-dimensional array" in {
    val df = Factory.createDf("person STRING,products ARRAY<STRUCT<title: STRING, sales: ARRAY<INT>>>",
      Row("John", Array(Row("car", Array(1, 2)), Row("house", Array(3, 4)))),
      Row("Mary", Array(Row("butter", Array(10, 20)), Row("bread", Array(30, 40)))))
    df.toJSON.collect() should contain inOrderOnly(
      """{"person":"John","products":[{"title":"car","sales":[1,2]},{"title":"house","sales":[3,4]}]}""",
      """{"person":"Mary","products":[{"title":"butter","sales":[10,20]},{"title":"bread","sales":[30,40]}]}""")
    val salesDf = df.select(col("person"), flatten(col("products")("sales")) as "sales")
    salesDf.schema.toDDL shouldEqual "person STRING,sales ARRAY<INT>"
    salesDf.toJSON.collect() should contain inOrderOnly(
      """{"person":"John","sales":[1,2,3,4]}""",
      """{"person":"Mary","sales":[10,20,30,40]}""")
  }
}