package spark3.sql.dataframe.operation.transformation.column

import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class ReferTopColumnTest extends AnyFlatSpec with SparkMatchers {

  it should "col() method" in {
    val df = Factory.peopleDf.withColumn("name_upper", upper(col("name")))
    df shouldContain(
      """{"name":"John","age":25,"gender":"M","name_upper":"JOHN"}""",
      """{"name":"Peter","age":35,"gender":"M","name_upper":"PETER"}""",
      """{"name":"Mary","age":20,"gender":"F","name_upper":"MARY"}""")
  }

  it should "column() method" in {
    val df = Factory.peopleDf.withColumn("name_upper", upper(column("name")))
    df shouldContain(
      """{"name":"John","age":25,"gender":"M","name_upper":"JOHN"}""",
      """{"name":"Peter","age":35,"gender":"M","name_upper":"PETER"}""",
      """{"name":"Mary","age":20,"gender":"F","name_upper":"MARY"}""")
  }

  it should "use $" in {
    import Factory.ss.implicits._
    val df = Factory.peopleDf.withColumn("name_upper", upper($"name"))
    df shouldContain(
      """{"name":"John","age":25,"gender":"M","name_upper":"JOHN"}""",
      """{"name":"Peter","age":35,"gender":"M","name_upper":"PETER"}""",
      """{"name":"Mary","age":20,"gender":"F","name_upper":"MARY"}""")
  }

  it should "use single quote" in {
    import Factory.ss.implicits._
    val df = Factory.peopleDf.withColumn("name_upper", upper('name))
    df shouldContain(
      """{"name":"John","age":25,"gender":"M","name_upper":"JOHN"}""",
      """{"name":"Peter","age":35,"gender":"M","name_upper":"PETER"}""",
      """{"name":"Mary","age":20,"gender":"F","name_upper":"MARY"}""")
  }

  it should "use DataFrame" in {
    val df1 = Factory.peopleDf
    val df2 = df1.withColumn("name_upper", upper(df1("name")))
    df2 shouldContain(
      """{"name":"John","age":25,"gender":"M","name_upper":"JOHN"}""",
      """{"name":"Peter","age":35,"gender":"M","name_upper":"PETER"}""",
      """{"name":"Mary","age":20,"gender":"F","name_upper":"MARY"}""")
  }

}