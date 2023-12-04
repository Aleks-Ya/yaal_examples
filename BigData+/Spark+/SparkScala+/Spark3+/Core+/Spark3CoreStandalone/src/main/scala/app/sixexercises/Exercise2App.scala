
package app.sixexercises

import org.apache.spark.sql.functions.{avg, broadcast, col, round}

/**
 * For each seller, what is the average % contribution of an order to the seller's daily quota?
 */
object Exercise2App {
  def main(args: Array[String]): Unit = {
    val salesDs = DatasetFactory.salesDs
    val sellersDs = DatasetFactory.sellersDs
    val salesSellersJoinDf = salesDs.join(broadcast(sellersDs), "seller_id")
    val groupBySellerDf = salesSellersJoinDf.groupBy("seller_id")
      .agg(round(avg(col("num_pieces_sold") / col("daily_target")) * 100, 4).as("order_contribution"))
    groupBySellerDf.show()
    val count = groupBySellerDf.count()
    print(s"Count: $count")
    //    assert(BigDecimal("1246.13385610000").compareTo(result) == 0)
  }
}
