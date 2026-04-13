package spark3.sql.dataframe.function.builtin.json

import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{col, from_json}
import org.apache.spark.sql.types.StructType.fromDDL
import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class FromJsonTest extends AnyFlatSpec with Matchers {

  it should "use from_json function" in {
    val df = Factory.createDf("name STRING,details STRING",
      Row("John", """{ "age": 30, "male": true, "job": { "title": "Engineer", "salary": 100000 } }"""),
      Row("Mary", """{ "age": 25, "male": false, "job": { "title": "Manager", "salary": 50000 } }"""),
      Row("Peter", """{ invalid JSON }"""),
      Row("Mark", null)
    )

    val detailsSchema = fromDDL("age INT,male BOOLEAN,job STRUCT<title: STRING, salary: INT>")
    val updatedDf = df.select(
      col("name"),
      from_json(col("details"), detailsSchema) as "details"
    )

    updatedDf.schema.toDDL shouldEqual "name STRING,details STRUCT<age: INT, male: BOOLEAN, job: STRUCT<title: STRING, salary: INT>>"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","details":{"age":30,"male":true,"job":{"title":"Engineer","salary":100000}}}""",
      """{"name":"Mary","details":{"age":25,"male":false,"job":{"title":"Manager","salary":50000}}}""",
      """{"name":"Peter","details":{"age":null,"male":null,"job":null}}""",
      """{"name":"Mark","details":null}"""
    )
  }

  it should "parse a JSON array" in {
    val df = Factory.createDf("name STRING,cities STRING",
      Row("John", """["London","Paris"]"""),
      Row("Peter", """{ invalid JSON }"""),
      Row("Mark", null)
    )

    val updatedDf = df.select(
      col("name"),
      from_json(col("cities"), ArrayType(StringType)) as "cities"
    )

    updatedDf.schema.toDDL shouldEqual "name STRING,cities ARRAY<STRING>"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","cities":["London","Paris"]}""",
      """{"name":"Peter","cities":null}""",
      """{"name":"Mark","cities":null}"""
    )
  }

  it should "parse only selected fields" in {
    val df = Factory.createDf("name STRING,details STRING",
      Row("John", """{ "age": 30, "male": true, "job": { "title": "Engineer", "salary": 100000 } }"""),
      Row("Peter", """{ invalid JSON }"""),
      Row("Mark", null)
    )

    val detailsSchema = fromDDL("age INT,male BOOLEAN,job STRUCT<title: STRING>")
    val updatedDf = df.select(
      col("name"),
      from_json(col("details"), detailsSchema) as "details"
    )

    updatedDf.schema.toDDL shouldEqual "name STRING,details STRUCT<age: INT, male: BOOLEAN, job: STRUCT<title: STRING>>"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","details":{"age":30,"male":true,"job":{"title":"Engineer"}}}""",
      """{"name":"Peter","details":{"age":null,"male":null,"job":null}}""",
      """{"name":"Mark","details":null}"""
    )
  }

}