package spark4.sql.dataframe.operation.transformation.join

import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{DataFrame, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory.createDf

class JoinLeftTest extends AnyFlatSpec with Matchers {
  private val presidentIdCol = "president_id"
  private val countriesDf: DataFrame = createDf(s"country STRING, $presidentIdCol INT",
    Row("USA", 1),
    Row("France", 2),
    Row("England", null))
  private val presidentsDf: DataFrame = createDf(s"$presidentIdCol INT, name STRING",
    Row(1, "Trump"),
    Row(2, "Macron"))

  it should "do left join" in {
    val joinedDf: DataFrame = countriesDf.join(presidentsDf, presidentIdCol, "left")
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"president_id":null,"country":"England","name":null}""",
      """{"president_id":1,"country":"USA","name":"Trump"}""",
      """{"president_id":2,"country":"France","name":"Macron"}"""
    )
  }

  it should "join DataFrame to itself" in {
    val df: DataFrame = createDf("id INT, name STRING, bossId INT",
      Row(1, "John", null),
      Row(2, "Mark", 1),
      Row(3, "Chad", 1))
    val joinedDf: DataFrame = df.as("a")
      .join(df.as("b"), col("a.bossId") === col("b.id"), "left")
      .select(col("a.id"), col("a.name"), col("b.name") as "boss")
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"id":1,"name":"John","boss":null}""",
      """{"id":2,"name":"Mark","boss":"John"}""",
      """{"id":3,"name":"Chad","boss":"John"}"""
    )
  }

}