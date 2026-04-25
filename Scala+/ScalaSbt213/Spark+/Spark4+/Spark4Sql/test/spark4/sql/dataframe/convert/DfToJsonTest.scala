package spark4.sql.dataframe.convert

import org.apache.spark.sql.{DataFrame, Dataset}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}


class DfToJsonTest extends AnyFlatSpec with SparkMatchers {

  it should "convert DataFrame to JSON" in {
    val df: DataFrame = Factory.cityObjectDf
    df shouldHaveDDL "name STRING,establishYear INT"

    val jsonDf: Dataset[String] = df.toJSON
    jsonDf shouldHaveDDL "value STRING"

    val jsonArray: Array[String] = jsonDf.collect
    jsonArray should contain allOf(
      """{"name":"Moscow","establishYear":1147}""",
      """{"name":"SPb","establishYear":1703}""")
  }
}
