package spark3.sql.dataframe.create.hardcode

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

import scala.beans.BeanProperty
import scala.jdk.CollectionConverters._

class CreateDataFrameTest extends AnyFlatSpec with Matchers {

  it should "apply schema to RDD" in {
    val schema = StructType(StructField("name", StringType) :: StructField("age", IntegerType) :: Nil)
    val rdd = Factory.ss.sparkContext.parallelize(Seq(Row("John", 25), Row("Peter", 35)))
    val df = Factory.ss.createDataFrame(rdd, schema)
    df.schema.simpleString shouldEqual "struct<name:string,age:int>"
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25}""",
      """{"name":"Peter","age":35}"""
    )
  }

  it should "apply schema to rows" in {
    val schema = StructType(StructField("name", StringType) :: StructField("age", IntegerType) :: Nil)
    val rows = Seq(Row("John", 25), Row("Peter", 35)).asJava
    val df = Factory.ss.createDataFrame(rows, schema)
    df.schema.simpleString shouldEqual "struct<name:string,age:int>"
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25}""",
      """{"name":"Peter","age":35}"""
    )
  }

  it should "create DataFrame from Object and Class" in {
    val data = Seq(People("John", 25), People("Peter", 35)).asJava
    val df = Factory.ss.createDataFrame(data, classOf[People])
    df.schema.simpleString shouldEqual "struct<age:int,name:string>"
    df.toJSON.collect should contain inOrderOnly(
      """{"age":25,"name":"John"}""",
      """{"age":35,"name":"Peter"}"""
    )
  }

  it should "create an DataFrame without columns" in {
    val schema = StructType(Nil)
    val rows = Seq(Row(), Row(), null)
    val rdd = Factory.ss.sparkContext.parallelize(rows)
    val df = Factory.ss.createDataFrame(rdd, schema)
    df.schema.simpleString shouldEqual "struct<>"
    df.schema.toDDL shouldBe empty
    df.toJSON.collect shouldEqual Seq(
      """{}""",
      """{}""",
      """{}"""
    )
  }

  it should "create an DataFrame without columns and rows" in {
    val schema: StructType = StructType(Nil)
    val rdd: RDD[Row] = Factory.ss.sparkContext.emptyRDD[Row]
    val df: DataFrame = Factory.ss.createDataFrame(rdd, schema)
    df.schema.simpleString shouldEqual "struct<>"
    df.schema.toDDL shouldBe empty
    df.toJSON.collect shouldBe empty
  }

  case class People(@BeanProperty var name: String, @BeanProperty var age: Int)
}