package app.six

import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.time.LocalDate

class WarmUpTest extends AnyFlatSpec with Matchers with BeforeAndAfterAll {

  it should "Find out how many sellers are in the data" in {
    val sellerCount = Factory.sellersDs.count()
    sellerCount shouldBe 10
  }

  it should "Find out how many products are in the data" in {
    val productCount = Factory.productsDs.count()
    productCount shouldBe 75000000
  }

  it should "Find out how many orders are in the data" in {
    val saleCount = Factory.salesDs.count()
    saleCount shouldBe 20000040
  }

  it should "How many products have been sold at least once" in {
    val productsSoldAtLeastOnce = Factory.salesDs
      .groupBy("product_id").sum("num_pieces_sold")
      .filter(row => row.getLong(row.fieldIndex("sum(num_pieces_sold)")) > 0)
      .count()
    productsSoldAtLeastOnce shouldBe 993429
  }

  it should "Which is the product contained in more orders" in {
    val productIdMaxOrders = Factory.salesDs.groupBy("product_id").count()
      .sort("count")
      .first()
      .getLong(0)
    productIdMaxOrders shouldBe 19609336
    val productNameMaxOrders = Factory.productsDs.filter(_.product_id == productIdMaxOrders).first().product_name
    productNameMaxOrders shouldBe "product_19609336"
  }

  it should "How many distinct products have been sold in each day" in {
    import Factory.ss.sqlContext.implicits._
    val dayToDistinctProductNumber = Factory.salesDs
      .groupByKey(_.date)
      .mapGroups((date, iter) => (date, iter.toList.map(_.product_id).distinct.length))
      .sort($"_2".desc)
      .collect()
    dayToDistinctProductNumber should contain inOrderOnly(
      (LocalDate.parse("2020-07-06"), 100765),
      (LocalDate.parse("2020-07-09"), 100501),
      (LocalDate.parse("2020-07-01"), 100337),
      (LocalDate.parse("2020-07-03"), 100017),
      (LocalDate.parse("2020-07-02"), 99807),
      (LocalDate.parse("2020-07-05"), 99796),
      (LocalDate.parse("2020-07-04"), 99791),
      (LocalDate.parse("2020-07-07"), 99756),
      (LocalDate.parse("2020-07-08"), 99662),
      (LocalDate.parse("2020-07-10"), 98973))
  }

  override protected def afterAll(): Unit = Factory.ss.stop()
}