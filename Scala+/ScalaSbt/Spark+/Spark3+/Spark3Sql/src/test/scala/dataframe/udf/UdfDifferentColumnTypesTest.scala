package dataframe.udf

import factory.Factory
import org.apache.spark.SparkException
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{ArrayType, IntegerType, LongType, StringType}
import org.apache.spark.sql.{Column, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UdfDifferentColumnTypesTest extends AnyFlatSpec with Matchers {

  it should "UDF can accept String column" in {
    val df = Factory.createDf(Map("name" -> StringType, "age" -> IntegerType),
      Row("John", 35), Row("Mary", 20))
    val updatedDf = df.withColumn("upper_name", UpperUdf(col("name")))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":35,"upper_name":"JOHN"}""",
      """{"name":"Mary","age":20,"upper_name":"MARY"}"""
    )
  }

  it should "UDF can accept String Array column" in {
    val df = Factory.createDf(Map("names" -> ArrayType(StringType), "age" -> IntegerType),
      Row(Seq("John", "Johnny"), 35), Row(Seq("Mary", "Marika"), 20))
    val updatedDf = df.withColumn("upper_names", UpperUdf(col("names")))
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"names":["John","Johnny"],"age":35,"upper_names":"JOHN,JOHNNY"}""",
      """{"names":["Mary","Marika"],"age":20,"upper_names":"MARY,MARIKA"}"""
    )
  }

  it should "UDF throws exception on unknown type" in {
    val df = Factory.createDf(Map("id" -> LongType, "age" -> IntegerType), Row(1L, 35), Row(2L, 20))
    val e = intercept[SparkException] {
      df.withColumn("upper_id", UpperUdf(col("id"))).collect()
    }
    val cause = e.getCause.getCause
    cause shouldBe a[IllegalArgumentException]
    cause should have message "Unknown type: 1"
  }

  object UpperUdf extends Serializable {
    def apply(name: Column): Column = udf((name: Any) => {
      name match {
        case nameStr: String => nameStr.toUpperCase
        case nameSeq: Seq[String] => nameSeq.mkString(",").toUpperCase
        case _ => throw new IllegalArgumentException(s"Unknown type: $name")
      }
    }).apply(name)
  }
}