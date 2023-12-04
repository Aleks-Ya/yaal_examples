
package app.sixexercises

/**
 * Which is the product contained in more orders.
 */
object WarmUp5App {
  def main(args: Array[String]): Unit = {
    val productIdMaxOrders = DatasetFactory.salesDs.groupBy("product_id").count()
      .sort("count")
      .first()
      .getLong(0)
    assert(productIdMaxOrders == 19609336)
    val productNameMaxOrders = DatasetFactory.productsDs.filter(_.product_id == productIdMaxOrders).first().product_name
    assert(productNameMaxOrders.equals("product_19609336"))
  }
}
