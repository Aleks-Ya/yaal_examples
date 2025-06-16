package dataframe.transformation.join

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, collect_list, explode}
import org.apache.spark.sql.types.{ArrayType, IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class JoinInnerTest extends AnyFlatSpec with Matchers {
  private val presidentIdCol = "president_id"
  private val countriesDf = Factory.createDf(Map("country" -> StringType, presidentIdCol -> IntegerType),
    Row("USA", 1),
    Row("France", 2),
    Row("England", null))
  private val presidentsDf = Factory.createDf(Map(presidentIdCol -> IntegerType, "name" -> StringType),
    Row(1, "Trump"),
    Row(2, "Macron"))

  it should "do inner join (default)" in {
    val joinedDf = countriesDf.join(presidentsDf, presidentIdCol)
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"president_id":1,"country":"USA","name":"Trump"}""",
      """{"president_id":2,"country":"France","name":"Macron"}"""
    )
  }

  it should "do inner join on columns having different names" in {
    val countriesDf = Factory.createDf(Map("country" -> StringType, "president_id" -> IntegerType),
      Row("USA", 1),
      Row("France", 2),
      Row("England", null))
    val presidentsDf = Factory.createDf(Map("id" -> IntegerType, "name" -> StringType),
      Row(1, "Trump"),
      Row(2, "Macron"))
    val joinedDf = countriesDf.join(presidentsDf, countriesDf("president_id") === presidentsDf("id"))
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"USA","president_id":1,"id":1,"name":"Trump"}""",
      """{"country":"France","president_id":2,"id":2,"name":"Macron"}"""
    )
  }

  it should "do inner join by several columns" in {
    val countryCol = "country"
    val cityCol = "city"
    val countriesDf = Factory.createDf(Map(countryCol -> StringType, cityCol -> StringType, "population" -> IntegerType),
      Row("France", "Paris", 10),
      Row("France", "Marseille", 20),
      Row("England", "London", 30),
      Row("England", "Manchester", 40))
    val mayorsDf = Factory.createDf(Map(countryCol -> StringType, cityCol -> StringType, "mayor" -> StringType),
      Row("France", "Paris", "Anne Hidalgo"),
      Row("France", "Marseille", "Benoit Payan"),
      Row("England", "London", "Anne Hidalgo"),
      Row("England", "Manchester", "Andy Burnham"))
    val joinedDf = countriesDf.join(mayorsDf, Seq(countryCol, cityCol))
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"England","city":"London","population":30,"mayor":"Anne Hidalgo"}""",
      """{"country":"England","city":"Manchester","population":40,"mayor":"Andy Burnham"}""",
      """{"country":"France","city":"Marseille","population":20,"mayor":"Benoit Payan"}""",
      """{"country":"France","city":"Paris","population":10,"mayor":"Anne Hidalgo"}"""
    )
  }


  it should "do inner join on array column" in {
    val countriesDf = Factory.createDf(Map("country" -> StringType, "president_ids" -> ArrayType(IntegerType)),
      Row("USA", Seq(1, 3)),
      Row("France", Seq(2)),
      Row("England", null))
    val presidentsDf = Factory.createDf(Map("id" -> IntegerType, "name" -> StringType),
      Row(1, "Trump"),
      Row(2, "Macron"),
      Row(3, "Trump")
    )

    val presidentsRenamedDf = presidentsDf
      .withColumnRenamed("id", "president_id")
      .withColumnRenamed("name", "president")
    val joinedDf = countriesDf
      .withColumn("president_id", explode(col("president_ids")))
      .join(presidentsRenamedDf, "president_id")
      .groupBy("country")
      .agg(collect_list(col("president")) as "presidents")
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"France","presidents":["Macron"]}""",
      """{"country":"USA","presidents":["Trump","Trump"]}"""
    )
  }
}