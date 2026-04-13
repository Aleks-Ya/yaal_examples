package spark3.sql.dataframe.operation.transformation.column

import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory

class WithColumnTest extends AnyFlatSpec with Matchers {

  it should "add a constant column" in {
    val df = Factory.peopleDf
    df.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
    val updatedDf = df
      .withColumn("text", lit("aaa"))
      .withColumn("number", lit(777))
    updatedDf.schema.toDDL shouldEqual "name STRING,age INT,gender STRING,text STRING NOT NULL,number INT NOT NULL"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","text":"aaa","number":777}""",
      """{"name":"Peter","age":35,"gender":"M","text":"aaa","number":777}""",
      """{"name":"Mary","age":20,"gender":"F","text":"aaa","number":777}"""
    )
  }

  it should "add a constant column with null value" in {
    val df = Factory.peopleDf
    df.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
    val updatedDf = df
      .withColumn("text", lit(null).cast(StringType))
      .withColumn("number", lit(null).cast(IntegerType))
    updatedDf.schema.toDDL shouldEqual "name STRING,age INT,gender STRING,text STRING,number INT"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","text":null,"number":null}""",
      """{"name":"Peter","age":35,"gender":"M","text":null,"number":null}""",
      """{"name":"Mary","age":20,"gender":"F","text":null,"number":null}"""
    )
  }

  it should "add an index column" in {
    val df = Factory.peopleDf
    df.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
    val updatedDf = df.withColumn("index", monotonically_increasing_id())
    updatedDf.schema.toDDL shouldEqual "name STRING,age INT,gender STRING,index BIGINT NOT NULL"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","index":0}""",
      """{"name":"Peter","age":35,"gender":"M","index":1}""",
      """{"name":"Mary","age":20,"gender":"F","index":2}"""
    )
  }

  it should "add a column based on another column" in {
    val df = Factory.peopleDf
    df.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
    val updatedDf = df.withColumn("double_age", col("age") * 2)
    updatedDf.schema.toDDL shouldEqual "name STRING,age INT,gender STRING,double_age INT"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","double_age":50}""",
      """{"name":"Peter","age":35,"gender":"M","double_age":70}""",
      """{"name":"Mary","age":20,"gender":"F","double_age":40}"""
    )
  }

  it should "add a column based on other columns" in {
    val df = Factory.peopleDf
    df.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
    val updatedDf = df.withColumn("gender_age", concat_ws("-", col("gender"), col("age")))
    updatedDf.schema.toDDL shouldEqual "name STRING,age INT,gender STRING,gender_age STRING NOT NULL"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","gender_age":"M-25"}""",
      """{"name":"Peter","age":35,"gender":"M","gender_age":"M-35"}""",
      """{"name":"Mary","age":20,"gender":"F","gender_age":"F-20"}"""
    )
  }

  it should "cast another column data type" in {
    val df = Factory.peopleDf
    df.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
    val updatedDf = df.withColumn("age_str", col("age").cast(StringType))
    updatedDf.schema.toDDL shouldEqual "name STRING,age INT,gender STRING,age_str STRING"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","age_str":"25"}""",
      """{"name":"Peter","age":35,"gender":"M","age_str":"35"}""",
      """{"name":"Mary","age":20,"gender":"F","age_str":"20"}"""
    )
  }

  it should "use added column in another added column" in {
    val df = Factory.peopleDf
    df.schema.toDDL shouldEqual "name STRING,age INT,gender STRING"
    val updatedDf = df
      .withColumn("text", lit("abc"))
      .withColumn("text_upper", upper(col("text")))
    updatedDf.schema.toDDL shouldEqual "name STRING,age INT,gender STRING,text STRING NOT NULL,text_upper STRING NOT NULL"
    updatedDf.toJSON.collect should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","text":"abc","text_upper":"ABC"}""",
      """{"name":"Peter","age":35,"gender":"M","text":"abc","text_upper":"ABC"}""",
      """{"name":"Mary","age":20,"gender":"F","text":"abc","text_upper":"ABC"}"""
    )
  }

}