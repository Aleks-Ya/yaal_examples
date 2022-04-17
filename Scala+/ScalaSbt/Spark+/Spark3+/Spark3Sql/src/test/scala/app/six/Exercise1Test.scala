package app.six

import org.apache.spark.sql.functions.{avg, col}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Exercise1Test extends AnyFlatSpec with Matchers with BeforeAndAfterAll {

  it should "What is the average revenue of the orders" in {
    val avgRevenue = Factory.salesDs
      .join(Factory.productsDs, "product_id")
      .withColumn("revenue", col("num_pieces_sold") * col("price"))
      .agg(avg("revenue"))
      .first()
      .getDecimal(0)
    avgRevenue shouldBe BigDecimal("1246.13385610000")
  }

  override protected def afterAll(): Unit = Factory.ss.stop()
}