package dataset.transformation

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class RandomSplitTransformationTest extends AnyFlatSpec with Matchers {

  it should "split a small dataset" in {
    import Factory.ss.implicits._
    val ds = Factory.ss.createDataset("abcdefghij".toCharArray.map(char => char.toString))
    ds.show
    val splits = ds.randomSplit(Array(0.5, 0.5), 1L)
    splits should have size 2
    val ds1 = splits(0)
    ds1.show
    val ds2 = splits(1)
    ds2.show
    (ds1.count + ds2.count) shouldEqual ds.count
    ds1.count shouldEqual ds.count / 2
    ds2.count shouldEqual ds.count / 2
    splits.flatMap(_.collect) should contain allElementsOf ds.collect
  }

  it should "split a big dataset" in {
    import Factory.ss.implicits._
    val size = 100000
    val arr = for (i <- 1 to size) yield i
    val ds = Factory.ss.createDataset(arr)
    ds.show
    ds.count shouldEqual size
    val splits = ds.randomSplit(Array(0.5, 0.5), 1L)
    splits should have size 2
    val ds1 = splits(0)
    ds1.show
    val ds2 = splits(1)
    ds2.show
    (ds1.count + ds2.count) shouldEqual size
  }

  it should "split into N DataSets" in {
    import Factory.ss.implicits._
    val size = 100
    val arr = for (i <- 1 to size) yield i
    val ds = Factory.ss.createDataset(arr)
    ds.show
    ds.count shouldEqual size
    val datasetsNumber = 5
    val weights = Array.fill(datasetsNumber)(1.0 / datasetsNumber)
    val splits = ds.randomSplit(weights)
    splits should have size datasetsNumber
    val ds1 = splits(0)
    ds1.show
    val ds2 = splits(1)
    ds2.show
    splits.map(_.count).sum shouldEqual size
    splits.flatMap(_.collect) should contain allElementsOf ds.collect
  }

}