package dataframe.convert

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DfToDsTest extends AnyFlatSpec with Matchers {

  it should "init dataset" in {
    val sqlContext = Factory.ss.sqlContext
    import sqlContext.implicits._
    val ds = Factory.ss.createDataset(Seq("a", "b"))
    ds.show
    val df = ds.toDF()
    df.show
  }

}