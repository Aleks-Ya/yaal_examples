package convert

import dataframe.DfFactory
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class DfToDsTest extends FlatSpec with BeforeAndAfterAll with Matchers {

  it should "init dataset" in {
    val sqlContext = DfFactory.ss.sqlContext
    import sqlContext.implicits._
    val ds = DfFactory.ss.createDataset(Seq("a", "b"))
    ds.show
    val df = ds.toDF()
    df.show
  }

}