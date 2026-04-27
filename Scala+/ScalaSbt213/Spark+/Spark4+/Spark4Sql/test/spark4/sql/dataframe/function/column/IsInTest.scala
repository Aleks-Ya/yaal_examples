package spark4.sql.dataframe.function.column

import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class IsInTest extends AnyFlatSpec with SparkMatchers {

  it should "filter by IN condition (vararg)" in {
    val updatedDf = Factory.peopleDf.filter(col("name").isin("John", "Mary"))
    updatedDf shouldHaveDDL "name STRING,age INT,gender STRING"
    updatedDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }

  it should "filter by IN condition (seq)" in {
    val names = Seq("John", "Mary")
    val updatedDf = Factory.peopleDf.filter(col("name").isin(names: _*))
    updatedDf shouldHaveDDL "name STRING,age INT,gender STRING"
    updatedDf shouldContain(
      """{"name":"John","age":25,"gender":"M"}""",
      """{"name":"Mary","age":20,"gender":"F"}""")
  }

}