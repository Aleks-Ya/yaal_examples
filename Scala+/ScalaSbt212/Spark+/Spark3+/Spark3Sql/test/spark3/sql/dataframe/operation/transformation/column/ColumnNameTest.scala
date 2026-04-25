package spark3.sql.dataframe.operation.transformation.column

import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class ColumnNameTest extends AnyFlatSpec with SparkMatchers {

  it should "treat column names case-insensitive" in {
    val df = Factory.peopleDf
    df shouldHaveDDL "name STRING,age INT,gender STRING"
    val df2 = df.select("GENDER", "NAME", "Age")
    df2 shouldContain(
      """{"GENDER":"M","NAME":"John","Age":25}""",
      """{"GENDER":"M","NAME":"Peter","Age":35}""",
      """{"GENDER":"F","NAME":"Mary","Age":20}"""
    )
  }

}