package dataset.transformation

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class RandomSplitTransformation extends AnyFlatSpec with Matchers {

  it should "split a small dataset" in {
    val sqlContext = Factory.ss.sqlContext
    import sqlContext.implicits._
    val ds = Factory.ss.createDataset("abcdefghij".toCharArray.map(char => char.toString))
    ds.show
    val split = ds.randomSplit(Array(0.5, 0.5), 1L)
    split should have size 2
    val ds1 = split(0)
    ds1.show
    val ds2 = split(1)
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
    val split = ds.randomSplit(Array(0.5, 0.5), 1L)
    split should have size 2
    val ds1 = split(0)
    ds1.show
    val ds2 = split(1)
    ds2.show
    (ds1.count + ds2.count) shouldEqual size
  }

}