package spark4.sql.dataset.convert

import org.apache.spark.sql.{DataFrame, Dataset}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{City, Factory, SparkMatchers}

class DsToDfTest extends AnyFlatSpec with SparkMatchers {

  it should "convert a String Dataset to a DataFrame" in {
    import Factory.ss.implicits._
    val ds: Dataset[String] = Factory.ss.createDataset(Seq("a", "b"))
    ds shouldHaveDDL "value STRING"
    val df: DataFrame = ds.toDF
    df shouldHaveDDL "value STRING"
    df shouldContain(
      """{"value":"a"}""",
      """{"value":"b"}""")
  }

  it should "convert an Object Dataset to a DataFrame" in {
    val ds: Dataset[City] = Factory.cityDs
    ds shouldHaveDDL "name STRING,establishYear INT NOT NULL"
    val df: DataFrame = ds.toDF
    df shouldHaveDDL "name STRING,establishYear INT NOT NULL"
    df shouldContain(
      """{"name":"Moscow","establishYear":1147}""",
      """{"name":"SPb","establishYear":1703}""",
      """{"name":"New York","establishYear":1665}""")
  }

}