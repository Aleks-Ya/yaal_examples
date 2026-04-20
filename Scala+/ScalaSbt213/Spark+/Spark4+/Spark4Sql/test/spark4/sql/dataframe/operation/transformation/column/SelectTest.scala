package spark4.sql.dataframe.operation.transformation.column

import org.apache.spark.sql.functions.{col, lit}
import org.apache.spark.sql.types._
import org.apache.spark.sql.{AnalysisException, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.sql.Factory
import spark4.sql.Factory.createDf

class SelectTest extends AnyFlatSpec with Matchers {

  it should "retain only 2 columns" in {
    val df = Factory.peopleDf.select(col("name"), col("gender"))
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"John","gender":"M"}""",
      """{"name":"Peter","gender":"M"}""",
      """{"name":"Mary","gender":"F"}""")
  }

  it should "select a sub-field" in {
    val info = StructType(
      StructField("age", IntegerType) ::
        StructField("gender", StringType) :: Nil)
    val schema = StructType(
      StructField("name", StringType) ::
        StructField("info", info) :: Nil)
    val df = createDf(schema, Row("John", Row(30, "M")), Row("Peter", Row(25, "M")), Row("Mary", Row(20, "F")))

    val df2 = df.select(col("name"), col("info").getField("age").as("age"))
    df2.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":30}""",
      """{"name":"Peter","age":25}""",
      """{"name":"Mary","age":20}""")
  }

  it should "select a sub-field of an array type" in {
    val department = StructType(
      StructField("city", StringType) ::
        StructField("year", IntegerType) :: Nil)
    val schema = StructType(
      StructField("name", StringType) ::
        StructField("departments", ArrayType(department)) :: Nil)
    val df = createDf(schema,
      Row("John", Seq(Row("London", 2010), Row("Paris", 2011))),
      Row("Peter", Seq(Row("Madrid", null), null)),
      Row("Mary", Seq()),
      Row("Sara", Seq(null)),
      Row("Ann", null))

    val df2 = df.select(col("name"), col("departments").getField("city").as("cities"))
    df2.toJSON.collect should contain inOrderOnly(
      """{"name":"John","cities":["London","Paris"]}""",
      """{"name":"Peter","cities":["Madrid",null]}""",
      """{"name":"Mary","cities":[]}""",
      """{"name":"Sara","cities":[null]}""",
      """{"name":"Ann","cities":null}""")
  }

  it should "create ambiguous columns by select" in {
    val df = Factory.peopleDf
    df.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
    val df2 = df.select(col("name"), col("age"), col("gender"), lit("abc").as("name"))
    df2.schema.toDDL shouldEqual "name STRING,age INT,gender STRING,name STRING NOT NULL"
    df2.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","name":"abc"}""",
      """{"name":"Peter","age":35,"gender":"M","name":"abc"}""",
      """{"name":"Mary","age":20,"gender":"F","name":"abc"}""")

    val e = the[AnalysisException] thrownBy {
      df2.select("name")
    }
    e.getMessage() shouldEqual "[AMBIGUOUS_REFERENCE] Reference `name` is ambiguous, could be: [`name`, `name`]."
  }

  it should "create a DataFrame with ambiguous columns" in {
    val df = Factory.createDf("product STRING, amount INT, AMOUNT INT",
      Row("Apple", 100, 1000),
      Row("Orange", null, null)
    )
    df.schema.simpleString shouldEqual "struct<product:string,amount:int,AMOUNT:int>"
    df.schema.toDDL shouldBe "product STRING,amount INT,AMOUNT INT"
    df.toJSON.collect should contain inOrderOnly(
      """{"product":"Apple","amount":100,"AMOUNT":1000}""",
      """{"product":"Orange","amount":null,"AMOUNT":null}""",
    )

    val e = the[AnalysisException] thrownBy {
      df.select("amount")
    }
    e.getMessage() shouldEqual "[AMBIGUOUS_REFERENCE] Reference `amount` is ambiguous, could be: [`amount`, `amount`]."
  }


}