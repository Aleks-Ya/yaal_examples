
package app.sixexercises

/**
 * Find out how many products are in the data.
 */
object WarmUp2App {
  def main(args: Array[String]): Unit = {
    val productCount = DataFrameFactory.productsDf.count()
    assert(productCount == 75000000)
  }
}
