package dataframe.function.builtin

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, exists}
import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExistsFunction extends AnyFlatSpec with Matchers {
  it should "use exists function" in {
    val df = Factory.createDf(Map("collection" -> StringType, "numbers" -> ArrayType(IntegerType)),
      Row("list 1", Seq(1, 4)),
      Row("list 2", Seq(5, 6)),
      Row("list 3", Seq(7, 9)))
    val updatedDf = df.select(col("collection"), exists(col("numbers"), _ % 2 === 0) as "has_even_numbers")
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"collection":"list 1","has_even_numbers":true}""",
      """{"collection":"list 2","has_even_numbers":true}""",
      """{"collection":"list 3","has_even_numbers":false}"""
    )
  }

  it should "filter row where a array contains an element" in {
    val df = Factory.createDf(Map("collection" -> StringType, "numbers" -> ArrayType(IntegerType)),
      Row("list 1", Seq(1, 4)),
      Row("list 2", Seq(5, 6)),
      Row("list 3", Seq(7, 5)))
    val updatedDf = df.filter(exists(col("numbers"), _ === 5))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"collection":"list 2","numbers":[5,6]}""",
      """{"collection":"list 3","numbers":[7,5]}"""
    )
  }

  it should "filter row where a array contains a struct element with non-null field" in {
    val df = Factory.createDf(Map(
      "collection" -> StringType,
      "people" -> ArrayType(StructType(Seq(
        StructField("name", StringType),
        StructField("age", IntegerType))))),
      Row("list 1", Seq(Row("John", 30), Row("Mary", null))),
      Row("list 2", Seq(Row("Mark", 20))),
      Row("list 3", Seq(Row("Rick", null), Row("Jack", null))))
    val updatedDf = df.filter(exists(col("people"), _("age").isNotNull))
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"collection":"list 1","people":[{"name":"John","age":30},{"name":"Mary","age":null}]}""",
      """{"collection":"list 2","people":[{"name":"Mark","age":20}]}"""
    )
  }
}