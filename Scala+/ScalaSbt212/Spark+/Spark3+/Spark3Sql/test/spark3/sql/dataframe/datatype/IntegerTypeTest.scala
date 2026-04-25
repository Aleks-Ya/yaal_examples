package spark3.sql.dataframe.datatype

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{IntegerType, StringType}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class IntegerTypeTest extends AnyFlatSpec with SparkMatchers {

  it should "INT column type" in {
    val df = Factory.createDf("name STRING, age INT",
      Row("John", 30),
      Row("Mary", 25))
    df shouldContain(
      """{"name":"John","age":30}""",
      """{"name":"Mary","age":25}""")
  }

  it should "IntegerType column type" in {
    val df = Factory.createDf(Map("name" -> StringType, "age" -> IntegerType),
      Row("John", 30),
      Row("Mary", 25))
    df shouldHaveDDL "name STRING,age INT"
    df shouldContain(
      """{"name":"John","age":30}""",
      """{"name":"Mary","age":25}""")
  }

}