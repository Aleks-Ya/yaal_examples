package dataset

import factory.Factory
import org.scalatest.{FlatSpec, Matchers}

class InCodeTest extends FlatSpec with Matchers {

  it should "init DataSet" in {
    val sqlContext = Factory.ss.sqlContext
    import sqlContext.implicits._
    val ds = Factory.ss.createDataset(Seq("a", "b"))
    ds.show
  }

  it should "init DataSet with Seq" in {
    val sqlContext = Factory.ss.sqlContext
    import sqlContext.implicits._
    val ds = Seq("c", "d").toDS()
    ds.show
  }

}