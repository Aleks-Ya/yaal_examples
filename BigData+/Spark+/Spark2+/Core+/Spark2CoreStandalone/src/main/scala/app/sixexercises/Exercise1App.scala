
package app.sixexercises

import org.apache.spark.sql.functions.{avg, col}

/**
 * What is the average revenue of the orders?
 */
object Exercise1App {
  def main(args: Array[String]): Unit = {
    val result = Factory.salesDs
      .join(Factory.productsDs, "product_id")
      .withColumn("revenue", col("num_pieces_sold") * col("price"))
      .agg(avg("revenue"))
      .first()
      .getDecimal(0)
    assert(BigDecimal("1246.13385610000").compareTo(result) == 0)
  }
}
