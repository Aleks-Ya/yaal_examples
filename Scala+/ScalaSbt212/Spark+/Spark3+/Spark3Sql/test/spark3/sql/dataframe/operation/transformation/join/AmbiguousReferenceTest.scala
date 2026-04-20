package spark3.sql.dataframe.operation.transformation.join

import org.apache.spark.sql.{AnalysisException, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory.createDf

class AmbiguousReferenceTest extends AnyFlatSpec with Matchers {
  private val countryDf = createDf("id INT, name STRING",
    Row(1, "USA"),
    Row(2, "France"))
  private val capitalDf = createDf("id INT, name STRING",
    Row(1, "Washington"),
    Row(2, "Paris"))

  it should "reproduce AMBIGUOUS_REFERENCE exception" in {
    val joinedDf = countryDf.join(capitalDf, "id")
    joinedDf.schema.toDDL shouldEqual "id INT,name STRING,name STRING"
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"id":1,"name":"USA","name":"Washington"}""",
      """{"id":2,"name":"France","name":"Paris"}""")

    val e = the[AnalysisException] thrownBy {
      joinedDf.select("name")
    }
    e.getMessage() shouldEqual "[AMBIGUOUS_REFERENCE] Reference `name` is ambiguous, could be: [`name`, `name`]."
  }

  it should "resolve AMBIGUOUS_REFERENCE exception" in {
    val joinedDf = countryDf.alias("a").join(capitalDf.alias("b"), "id")
    joinedDf.schema.toDDL shouldEqual "id INT,name STRING,name STRING"
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"id":1,"name":"USA","name":"Washington"}""",
      """{"id":2,"name":"France","name":"Paris"}""")

    val ambiguousDf = joinedDf.select("a.id", "a.name", "b.name")
    ambiguousDf.schema.toDDL shouldEqual "id INT,name STRING,name STRING"
    ambiguousDf.toJSON.collect should contain inOrderOnly(
      """{"id":1,"name":"USA","name":"Washington"}""",
      """{"id":2,"name":"France","name":"Paris"}""")
  }

  it should "resolve AMBIGUOUS_REFERENCE exception by renaming one column" in {
    val joinedDf = countryDf.alias("a").join(capitalDf.alias("b"), "id")
    joinedDf.schema.toDDL shouldEqual "id INT,name STRING,name STRING"
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"id":1,"name":"USA","name":"Washington"}""",
      """{"id":2,"name":"France","name":"Paris"}""")

    val ambiguousDf = joinedDf.select("a.id", "a.name", "b.name")
    ambiguousDf.schema.toDDL shouldEqual "id INT,name STRING,name STRING"
    ambiguousDf.toJSON.collect should contain inOrderOnly(
      """{"id":1,"name":"USA","name":"Washington"}""",
      """{"id":2,"name":"France","name":"Paris"}""")
  }

  it should "handle column names case-insensitive" in {
    val countryDf = createDf("id INT, name STRING",
      Row(1, "USA"),
      Row(2, "France"))
    val capitalDf = createDf("ID INT, NAME STRING",
      Row(1, "Washington"),
      Row(2, "Paris"))
    val joinedDf = countryDf.join(capitalDf, "id")
    joinedDf.schema.toDDL shouldEqual "id INT,name STRING,NAME STRING"
    joinedDf.toJSON.collect should contain inOrderOnly(
      """{"id":1,"name":"USA","NAME":"Washington"}""",
      """{"id":2,"name":"France","NAME":"Paris"}""")

    val e = the[AnalysisException] thrownBy {
      joinedDf.select("name")
    }
    e.getMessage() shouldEqual "[AMBIGUOUS_REFERENCE] Reference `name` is ambiguous, could be: [`name`, `name`]."
  }

}