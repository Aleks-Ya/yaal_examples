package dataframe.transformation

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{avg, collect_list, max, min}
import org.apache.spark.sql.types.{IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GroupByTransformation extends AnyFlatSpec with Matchers {

  it should "use groupBy transformation" in {
    val df = Factory.peopleDf
    val updatedDf = df.groupBy("gender").count()
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"gender":"F","count":1}""",
      """{"gender":"M","count":2}"""
    )
  }

  it should "use groupBy with agg" in {
    val ageCol = "age"
    val df = Factory.peopleDf
    val updatedDf = df.groupBy("gender").agg(
      max(ageCol) as "max_age",
      avg(ageCol) as "avg_age",
      min(ageCol) as "min_age")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"gender":"F","max_age":20,"avg_age":20.0,"min_age":20}""",
      """{"gender":"M","max_age":35,"avg_age":30.0,"min_age":25}"""
    )
  }

  it should "groupBy by several columns" in {
    val df = Factory.createDf(Map("name" -> StringType, "age" -> IntegerType, "gender" -> StringType),
      Row("John", 30, "M"),
      Row("Mary", 30, "F"),
      Row("Mark", 25, "M"),
      Row("Chad", 30, "M"))
    val updatedDf = df.groupBy("gender", "age").agg(collect_list("name") as "names")
    updatedDf.toJSON.collect() should contain inOrderOnly(
      """{"gender":"M","age":25,"names":["Mark"]}""",
      """{"gender":"M","age":30,"names":["John","Chad"]}""",
      """{"gender":"F","age":30,"names":["Mary"]}"""
    )
  }
}