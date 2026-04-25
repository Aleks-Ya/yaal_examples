package spark4.sql.dataframe.function.builtin

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{coalesce, col}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class CoalesceTest extends AnyFlatSpec with SparkMatchers {
  it should "use coalesce function" in {
    val df = Factory.createDf("city1 STRING,city2 STRING,city3 STRING",
      Row("London1", "Berlin1", "Washington1"),
      Row(null, "Berlin2", "Washington2"),
      Row("London3", null, "Washington3"),
      Row("London4", "Berlin4", null),
      Row(null, null, "Washington5"),
      Row(null, "", "Washington5"), //empty string
      Row(null, null, null))
    val updatedDf = df.select(coalesce(col("city1"), col("city2"), col("city3")) as "city")
    updatedDf shouldContain(
      """{"city":"London1"}""",
      """{"city":"Berlin2"}""",
      """{"city":"London3"}""",
      """{"city":"London4"}""",
      """{"city":"Washington5"}""",
      """{"city":""}""",
      """{"city":null}""")
  }
}