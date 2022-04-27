package dataset.action

import factory.Factory
import org.apache.spark.sql.SparkSession
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ReduceAction extends AnyFlatSpec with Matchers {

  private val ss: SparkSession = Factory.ss

  import ss.sqlContext.implicits._

  it should "reduce Dataset contains >2 elements" in {
    val ds = ss.createDataset(Seq(1, 2, 3))
    val sum = ds.reduce((n1, n2) => n1 + n2)
    sum shouldEqual 6
  }

  it should "reduce Dataset contain 2 elements" in {
    val ds = ss.createDataset(Seq(1, 2))
    val sum = ds.reduce((n1, n2) => n1 + n2)
    sum shouldEqual 3
  }

  it should "reduce Dataset contain 1 element" in {
    val ds = ss.createDataset(Seq(2))
    val sum = ds.reduce((n1, n2) => n1 + n2)
    sum shouldEqual 2
  }

  it should "can't reduce an empty Dataset" in {
    val caught = intercept[UnsupportedOperationException] {
      ss.createDataset(Seq[Int]())
        .reduce((n1, n2) => n1 + n2)
    }
    caught.getMessage shouldEqual "empty collection"
  }

  it should "reduce an empty Dataset" in {
    val ds = ss.createDataset(Seq[Int]())
    val sum = if (!ds.isEmpty) ds.reduce((n1, n2) => n1 + n2) else 0
    sum shouldEqual 0
  }
}
