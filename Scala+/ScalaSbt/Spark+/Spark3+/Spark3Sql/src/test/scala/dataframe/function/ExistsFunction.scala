package dataframe.function

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, exists}
import org.apache.spark.sql.types.{ArrayType, IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExistsFunction extends AnyFlatSpec with Matchers {
  it should "use exists function" in {
    val df = Factory.createDf(Map("collection" -> StringType, "numbers" -> ArrayType(IntegerType)),
      Row("list 1", Seq(1, 4)),
      Row("list 2", Seq(5, 6)),
      Row("list 3", Seq(7, 9)))
    val updatedDf = df.select(col("collection"), exists(col("numbers"), _ % 2 === 0) as "has_even_numbers")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"collection":"list 1","has_even_numbers":true}""",
      """{"collection":"list 2","has_even_numbers":true}""",
      """{"collection":"list 3","has_even_numbers":false}"""
    )
  }
}