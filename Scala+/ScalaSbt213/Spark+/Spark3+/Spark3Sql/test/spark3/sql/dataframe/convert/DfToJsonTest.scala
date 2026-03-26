package spark3.sql.dataframe.convert

import org.apache.spark.sql.{DataFrame, Dataset}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory


class DfToJsonTest extends AnyFlatSpec with Matchers {

  it should "convert DataFrame to JSON" in {
    val df: DataFrame = Factory.cityObjectDf
    df.schema.toDDL shouldEqual "name STRING,establishYear INT"

    val jsonDf: Dataset[String] = df.toJSON
    jsonDf.schema.toDDL shouldEqual "value STRING"

    val jsonArray: Array[String] = jsonDf.collect
    jsonArray should contain allOf(
      """{"name":"Moscow","establishYear":1147}""",
      """{"name":"SPb","establishYear":1703}""")
  }

}
