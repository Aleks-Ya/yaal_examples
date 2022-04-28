package dataframe.transformation

import factory.Factory
import factory.Factory.ss
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{asc, col, desc}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class FilterTransformation extends AnyFlatSpec with Matchers {

  it should "filter a DataFrame" in {
    val df = Factory.peopleDf.filter(col("age") > 20)
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}"""
    )
  }

  it should "exclude rows with null in a column" in {
    val df = Factory.createDf(Map("name" -> StringType, "age" -> IntegerType),
      Row("John", 35), Row("Peter", null), Row("Mary", 20))

    val filteredDf = df.filter(col("age").isNotNull)
    filteredDf.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":35}""",
      """{"name":"Mary","age":20}"""
    )
  }

}