
package app.sixexercises

import org.apache.spark.sql.functions.{broadcast, col, desc, sum}

object Exercise3App {
  def main(args: Array[String]): Unit = {
    val salesDs = DatasetFactory.salesDs
    val sellersDs = DatasetFactory.sellersDs
    val productsDs = DatasetFactory.productsDs
    val salesSellersJoinDf = salesDs.join(broadcast(sellersDs), "seller_id")
    val salesSellersProductsJoinDf = salesSellersJoinDf.join(productsDs, "product_id")
    val soldAmountDf = salesSellersProductsJoinDf
      .withColumn("sold_amount", col("price") * col("num_pieces_sold"))
    val productSellerGroupedDf = soldAmountDf
      .groupBy("product_id", "seller_id")
      .agg(sum("sold_amount").as("sold_amount_sum"))
    val sortedDf = productSellerGroupedDf.sort(desc("sold_amount_sum")).cache()
    sortedDf.show()

    val head2 = sortedDf.head(2)
    val top2 = head2(1)
    val seller2 = top2.getString(top2.fieldIndex("seller_id"))
    print(s"The second top seller: $seller2")

    val last = sortedDf.tail(1)(0)
    print(s"The last: ${last.mkString}")
    //    assert(BigDecimal("1246.13385610000").compareTo(result) == 0)
  }
}
