package spark3.sql.dataset.convert

import org.apache.spark.sql.{DataFrame, Dataset}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.{City, Factory}

class DsToDfTest extends AnyFlatSpec with Matchers {

  it should "convert a String Dataset to a DataFrame" in {
    import Factory.ss.implicits._
    val ds: Dataset[String] = Factory.ss.createDataset(Seq("a", "b"))
    ds.schema.toDDL shouldEqual "value STRING"
    ds.show

    val df: DataFrame = ds.toDF
    df.schema.toDDL shouldEqual "value STRING"
    df.toJSON.collect should contain inOrderOnly(
      """{"value":"a"}""",
      """{"value":"b"}"""
    )
  }

  it should "convert an Object Dataset to a DataFrame" in {
    val ds: Dataset[City] = Factory.cityDs
    ds.schema.toDDL shouldEqual "name STRING,establishYear INT NOT NULL"
    ds.show

    val df: DataFrame = ds.toDF
    df.schema.toDDL shouldEqual "name STRING,establishYear INT NOT NULL"
    df.toJSON.collect should contain inOrderOnly(
      """{"name":"Moscow","establishYear":1147}""",
      """{"name":"SPb","establishYear":1703}""",
      """{"name":"New York","establishYear":1665}"""
    )
  }

}