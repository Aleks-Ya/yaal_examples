package dataframe.transformation.column

import factory.Factory
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StringType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AddColumnTest extends AnyFlatSpec with Matchers {

  it should "add a constant column" in {
    val df = Factory.peopleDf
      .withColumn("text", lit("aaa"))
      .withColumn("number", lit(777))
    df.show
    df.printSchema
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","text":"aaa","number":777}""",
      """{"name":"Peter","age":35,"gender":"M","text":"aaa","number":777}""",
      """{"name":"Mary","age":20,"gender":"F","text":"aaa","number":777}"""
    )
  }

  it should "add an index column" in {
    val df = Factory.peopleDf
      .withColumn("index", monotonically_increasing_id())
    df.show
    df.printSchema
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","index":0}""",
      """{"name":"Peter","age":35,"gender":"M","index":1}""",
      """{"name":"Mary","age":20,"gender":"F","index":2}"""
    )
  }

  it should "add a column based on another column" in {
    val df = Factory.peopleDf
      .withColumn("double_age", col("age") * 2)
    df.show
    df.printSchema
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","double_age":50}""",
      """{"name":"Peter","age":35,"gender":"M","double_age":70}""",
      """{"name":"Mary","age":20,"gender":"F","double_age":40}"""
    )
  }

  it should "add a column based on other columns" in {
    val df = Factory.peopleDf
      .withColumn("gender_age", concat_ws("-", col("gender"), col("age")))
    df.show
    df.printSchema
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","gender_age":"M-25"}""",
      """{"name":"Peter","age":35,"gender":"M","gender_age":"M-35"}""",
      """{"name":"Mary","age":20,"gender":"F","gender_age":"F-20"}"""
    )
  }

  it should "cast another column data type" in {
    val df = Factory.peopleDf
      .withColumn("age_str", col("age").cast(StringType))
    df.show
    df.printSchema
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","age_str":"25"}""",
      """{"name":"Peter","age":35,"gender":"M","age_str":"35"}""",
      """{"name":"Mary","age":20,"gender":"F","age_str":"20"}"""
    )
  }

  it should "use added column in another added column" in {
    val df = Factory.peopleDf
      .withColumn("text", lit("abc"))
      .withColumn("text_upper", upper(col("text")))
    df.show
    df.printSchema
    df.toJSON.collect() should contain inOrderOnly(
      """{"name":"John","age":25,"gender":"M","text":"abc","text_upper":"ABC"}""",
      """{"name":"Peter","age":35,"gender":"M","text":"abc","text_upper":"ABC"}""",
      """{"name":"Mary","age":20,"gender":"F","text":"abc","text_upper":"ABC"}"""
    )
  }
}