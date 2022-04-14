package app.six

import java.time.LocalDate

case class Seller(seller_id: Long, seller_name: String, daily_target: Long)

case class Product(product_id: Long, product_name: String, price: BigDecimal)

case class Sale(order_id: Long, product_id: Long, seller_id: Long, num_pieces_sold: Int, bill_raw_text: String, date: LocalDate)
