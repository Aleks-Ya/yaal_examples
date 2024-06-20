package dataframe.create

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.beans.BeanProperty
import scala.collection.JavaConverters._

class CreateDataFrameTest extends AnyFlatSpec with Matchers {

  it should "apply schema to RDD" in {
    val schema = StructType(StructField("name", StringType) :: StructField("age", IntegerType) :: Nil)
    val rdd = Factory.ss.sparkContext.parallelize(Seq(Row("John", 25), Row("Peter", 35)))
    val df = Factory.ss.createDataFrame(rdd, schema)
    df.schema.simpleString shouldEqual "struct<name:string,age:int>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25}""",
      """{"name":"Peter","age":35}"""
    )
  }

  it should "apply schema to rows" in {
    val schema = StructType(StructField("name", StringType) :: StructField("age", IntegerType) :: Nil)
    val rows = Seq(Row("John", 25), Row("Peter", 35)).asJava
    val df = Factory.ss.createDataFrame(rows, schema)
    df.schema.simpleString shouldEqual "struct<name:string,age:int>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25}""",
      """{"name":"Peter","age":35}"""
    )
  }

  it should "create DataFrame from Object and Class" in {
    val data = Seq(People("John", 25), People("Peter", 35)).asJava
    val df = Factory.ss.createDataFrame(data, classOf[People])
    df.schema.simpleString shouldEqual "struct<age:int,name:string>"
    df.toJSON.collect() should contain inOrderOnly(
      """{"age":25,"name":"John"}""",
      """{"age":35,"name":"Peter"}"""
    )
  }

  case class People(@BeanProperty var name: String, @BeanProperty var age: Int)
}