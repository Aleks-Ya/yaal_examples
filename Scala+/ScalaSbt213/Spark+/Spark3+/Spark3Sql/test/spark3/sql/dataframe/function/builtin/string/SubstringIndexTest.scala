package spark3.sql.dataframe.function.builtin.string

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, substring_index}
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class SubstringIndexTest extends AnyFlatSpec with SparkMatchers {
  it should "use filter function" in {
    val df = Factory.createDf(Map("text" -> StringType),
      Row("a,b,c"),
      Row("e,f,g"))
    val updatedDf = df.select(
      substring_index(col("text"), ",", 2) as "index")
    updatedDf shouldContain(
      """{"index":"a,b"}""",
      """{"index":"e,f"}"""
    )
  }
}