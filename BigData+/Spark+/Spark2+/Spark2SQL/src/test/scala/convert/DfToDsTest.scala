package convert

import factory.Factory
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class DfToDsTest extends FlatSpec with BeforeAndAfterAll with Matchers {

  it should "init dataset" in {
    val sqlContext = Factory.ss.sqlContext
    import sqlContext.implicits._
    val ds = Factory.ss.createDataset(Seq("a", "b"))
    ds.show
    val df = ds.toDF()
    df.show
  }

}