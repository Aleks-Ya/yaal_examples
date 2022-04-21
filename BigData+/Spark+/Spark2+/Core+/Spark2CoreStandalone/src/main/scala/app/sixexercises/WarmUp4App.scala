
package app.sixexercises

/**
 * How many products have been sold at least once.
 */
object WarmUp4App {
  def main(args: Array[String]): Unit = {
    val productsSoldAtLeastOnce = Factory.salesDs
      .groupBy("product_id").sum("num_pieces_sold")
      .filter(row => row.getLong(row.fieldIndex("sum(num_pieces_sold)")) > 0)
      .count()
    assert(productsSoldAtLeastOnce == 993429)
  }
}
