package spark4.sql.dataframe.operation.transformation

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class TransformTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "transform a DataFrame (inline)" in {
    val df = Factory.peopleDf
    val updatedDf = df.transform(_.filter(col("age") > 20))
    updatedDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""")
  }

  it should "transform a DataFrame (method)" in {
    val df = Factory.peopleDf
    val updatedDf = df.transform(filterByAge)
    updatedDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Peter","age":35,"gender":"M"}""")
  }

  private def filterByAge(df: DataFrame): DataFrame = {
    df.filter(col("age") > 20)
  }
}