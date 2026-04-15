package spark3.sql.dataframe.operation.transformation.join

import org.apache.spark.sql.functions.{col, collect_list, explode}
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory.createDf

class JoinInnerTest extends AnyFlatSpec with Matchers {
  private val presidentIdCol = "president_id"
  private val countriesDf: DataFrame = createDf(s"country STRING, $presidentIdCol INT",
    Row("USA", 1),
    Row("France", 2),
    Row("England", null))
  private val presidentsDf: DataFrame = createDf(s"$presidentIdCol INT, name STRING",
    Row(1, "Trump"),
    Row(2, "Macron"))

  it should "do inner join (default)" in {
    val joinedDf: DataFrame = countriesDf.join(presidentsDf, presidentIdCol)
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"president_id":1,"country":"USA","name":"Trump"}""",
      """{"president_id":2,"country":"France","name":"Macron"}"""
    )
  }

  it should "do inner join on columns having different names" in {
    val countriesDf: DataFrame = createDf("country STRING, president_id INT",
      Row("USA", 1),
      Row("France", 2),
      Row("England", null))
    val presidentsDf: DataFrame = createDf("id INT,name STRING",
      Row(1, "Trump"),
      Row(2, "Macron"))
    val joinedDf: DataFrame = countriesDf.join(presidentsDf, countriesDf("president_id") === presidentsDf("id"))
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"USA","president_id":1,"id":1,"name":"Trump"}""",
      """{"country":"France","president_id":2,"id":2,"name":"Macron"}"""
    )
  }

  it should "do inner join by several columns" in {
    val countryCol = "country"
    val cityCol = "city"
    val countriesDf: DataFrame = createDf(s"$countryCol STRING, $cityCol STRING, population INT",
      Row("France", "Paris", 10),
      Row("France", "Marseille", 20),
      Row("England", "London", 30),
      Row("England", "Manchester", 40))
    val mayorsDf: DataFrame = createDf(s"$countryCol STRING, $cityCol STRING, mayor STRING",
      Row("France", "Paris", "Anne Hidalgo"),
      Row("France", "Marseille", "Benoit Payan"),
      Row("England", "London", "Anne Hidalgo"),
      Row("England", "Manchester", "Andy Burnham"))
    val joinedDf: DataFrame = countriesDf.join(mayorsDf, Seq(countryCol, cityCol))
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"country":"England","city":"London","population":30,"mayor":"Anne Hidalgo"}""",
      """{"country":"England","city":"Manchester","population":40,"mayor":"Andy Burnham"}""",
      """{"country":"France","city":"Marseille","population":20,"mayor":"Benoit Payan"}""",
      """{"country":"France","city":"Paris","population":10,"mayor":"Anne Hidalgo"}"""
    )
  }

  it should "do inner join on array column" in {
    val countriesDf: DataFrame = createDf("country STRING, president_ids ARRAY<INT>",
      Row("USA", Seq(1, 3)),
      Row("France", Seq(2)),
      Row("England", null))
    val presidentsDf: DataFrame = createDf("id INT, name STRING",
      Row(1, "Trump"),
      Row(2, "Macron"),
      Row(3, "Trump")
    )

    val presidentsRenamedDf: DataFrame = presidentsDf
      .withColumnRenamed("id", "president_id")
      .withColumnRenamed("name", "president")
    val joinedDf: DataFrame = countriesDf
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