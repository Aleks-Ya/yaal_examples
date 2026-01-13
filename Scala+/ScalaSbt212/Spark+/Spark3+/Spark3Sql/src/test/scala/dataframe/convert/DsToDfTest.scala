package dataframe.convert

import factory.Factory
import org.apache.spark.sql.{DataFrame, Dataset}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DsToDfTest extends AnyFlatSpec with Matchers {

  it should "convert Dataset to DataFrame" in {
    import Factory.ss.implicits._
    val ds: Dataset[String] = Factory.ss.createDataset(Seq("a", "b"))
    ds.show
    val df: DataFrame = ds.toDF()
    df.show
  }

}