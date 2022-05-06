package dataframe.datatype

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{ArrayType, DataTypes, IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Handle ArrayType.
 */
class ArrayTypeTest extends AnyFlatSpec with Matchers {

  it should "create an ArrayType field (with ArrayType constructor)" in {
    val df = Factory.createDf(Map("name" -> StringType, "orders" -> ArrayType(IntegerType)),
      Row("USA", Array(10, 20)), Row("Canada", Array(30, 40)))
    df.show()
    df.printSchema()
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"USA","orders":[10,20]}""",
      """{"name":"Canada","orders":[30,40]}"""
    )
  }

  it should "create an ArrayType field (with DataTypes class)" in {
    val df = Factory.createDf(Map("name" -> StringType, "orders" -> DataTypes.createArrayType(IntegerType)),
      Row("USA", Array(10, 20)), Row("Canada", Array(30, 40)))
    df.show()
    df.printSchema()
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"USA","orders":[10,20]}""",
      """{"name":"Canada","orders":[30,40]}"""
    )
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
    df.toJSON.collect() should contain inOrderOnly(
      """{"numbers":[10,20]}""",
      """{"numbers":[30,40]}"""
    )
    import Factory.ss.sqlContext.implicits._
    val sumDf = df.map(row => row.getSeq[Int](row.fieldIndex("numbers")).sum)
    sumDf.toJSON.collect() should contain inOrderOnly(
      """{"value":30}""",
      """{"value":70}"""
    )
  }

}