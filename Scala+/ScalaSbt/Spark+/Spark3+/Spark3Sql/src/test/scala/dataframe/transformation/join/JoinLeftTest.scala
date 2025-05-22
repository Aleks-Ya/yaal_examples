package dataframe.transformation.join

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.types.{IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class JoinLeftTest extends AnyFlatSpec with Matchers {
  private val presidentIdCol = "president_id"
  private val countriesDf = Factory.createDf(Map("country" -> StringType, presidentIdCol -> IntegerType),
    Row("USA", 1),
    Row("France", 2),
    Row("England", null))
  private val presidentsDf = Factory.createDf(Map(presidentIdCol -> IntegerType, "name" -> StringType),
    Row(1, "Trump"),
    Row(2, "Macron"))

  it should "do left join" in {
    val joinedDf = countriesDf.join(presidentsDf, presidentIdCol, "left")
    joinedDf.toJSON.collect() should contain inOrderOnly(
      """{"president_id":null,"country":"England","name":null}""",
      """{"president_id":1,"country":"USA","name":"Trump"}""",
      """{"president_id":2,"country":"France","name":"Macron"}"""
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