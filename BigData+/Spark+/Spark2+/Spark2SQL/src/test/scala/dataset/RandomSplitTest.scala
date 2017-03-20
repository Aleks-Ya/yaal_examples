package dataset

import factory.Factory
import org.scalatest.{FlatSpec, Matchers}

class RandomSplitTest extends FlatSpec with Matchers {

  it should "split a small dataset" in {
    val sqlContext = Factory.ss.sqlContext
    import sqlContext.implicits._
    val ds = Factory.ss.createDataset("abcdefghij".toCharArray.map(char => char.toString))
    ds.show
    val splitted = ds.randomSplit(Array(0.5, 0.5), 1L)
    splitted should have size 2
    val ds1 = splitted(0)
    ds1.show
    val ds2 = splitted(1)
    ds2.show
    (ds1.count + ds2.count) shouldEqual ds.count
    ds1.count shouldEqual ds.count / 2
    ds2.count shouldEqual ds.count / 2
  }

  it should "split a big dataset" in {
    val sqlContext = Factory.ss.sqlContext
    import sqlContext.implicits._
    val size = 100000
    val arr = for (i <- 1 to size) yield i
    val ds = Factory.ss.createDataset(arr)
    ds.show
    ds.count shouldEqual size
    val splitted = ds.randomSplit(Array(0.5, 0.5), 1L)
    splitted should have size 2
    val ds1 = splitted(0)
    ds1.show
    val ds2 = splitted(1)
    ds2.show
    (ds1.count + ds2.count) shouldEqual size
  }

}