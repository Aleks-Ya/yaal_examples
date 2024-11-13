package dataframe.transformation.join

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types.{IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class JoinTest extends AnyFlatSpec with Matchers {
  private val presidentIdCol = "president_id"
  private val countriesDf = Factory.createDf(Map("country" -> StringType, presidentIdCol -> IntegerType),
    Row("USA", 1),
    Row("France", 2),
    Row("England", null))
  private val presidentsDf = Factory.createDf(Map(presidentIdCol -> IntegerType, "name" -> StringType),
    Row(1, "Biden"),
    Row(2, "Macron"))

  it should "do inner join (default)" in {
    val joinedDf = countriesDf.join(presidentsDf, presidentIdCol)
    joinedDf.toJSON.collect() should contain inOrderOnly(
      """{"president_id":1,"country":"USA","name":"Biden"}""",
      """{"president_id":2,"country":"France","name":"Macron"}"""
    )
  }

  it should "do left join" in {
    val joinedDf = countriesDf.join(presidentsDf, presidentIdCol, "left")
    joinedDf.toJSON.collect() should contain inOrderOnly(
      """{"president_id":null,"country":"England","name":null}""",
      """{"president_id":1,"country":"USA","name":"Biden"}""",
      """{"president_id":2,"country":"France","name":"Macron"}"""
    )
  }

  it should "do inner join on columns having different names" in {
    val countriesDf = Factory.createDf(Map("country" -> StringType, "president_id" -> IntegerType),
      Row("USA", 1),
      Row("France", 2),
      Row("England", null))
    val presidentsDf = Factory.createDf(Map("id" -> IntegerType, "name" -> StringType),
      Row(1, "Biden"),
      Row(2, "Macron"))
    val joinedDf = countriesDf.join(presidentsDf, countriesDf("president_id") === presidentsDf("id"))
    joinedDf.toJSON.collect() should contain inOrderOnly(
      """{"country":"USA","president_id":1,"id":1,"name":"Biden"}""",
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
    joinedDf.toJSON.collect() should contain inOrderOnly(
      """{"country":"England","city":"London","population":30,"mayor":"Anne Hidalgo"}""",
      """{"country":"England","city":"Manchester","population":40,"mayor":"Andy Burnham"}""",
      """{"country":"France","city":"Marseille","population":20,"mayor":"Benoit Payan"}""",
      """{"country":"France","city":"Paris","population":10,"mayor":"Anne Hidalgo"}"""
    )
  }

  it should "join DataFrame to itself" in {
    val df = Factory.createDf(Map("id" -> IntegerType, "name" -> StringType, "bossId" -> IntegerType),
      Row(1, "John", null),
      Row(2, "Mark", 1),
      Row(3, "Chad", 1))
    val joinedDf = df.as("a")
      .join(df.as("b"), col("a.bossId") === col("b.id"), "left")
      .select(col("a.id"), col("a.name"), col("b.name") as "boss")
    joinedDf.toJSON.collect() should contain inOrderOnly(
      """{"id":1,"name":"John","boss":null}""",
      """{"id":2,"name":"Mark","boss":"John"}""",
      """{"id":3,"name":"Chad","boss":"John"}"""
    )
  }
}