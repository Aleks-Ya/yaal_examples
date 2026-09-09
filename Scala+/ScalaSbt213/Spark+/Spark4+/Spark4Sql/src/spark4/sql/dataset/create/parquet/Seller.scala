package spark4.sql.dataset.create.parquet

case class Seller(seller_id: String, seller_name: String, daily_target: String)

case class SellerDetailed(seller_id: String,
                          seller_name: String,
                          unused_int: Integer,
                          daily_target: String,
                          unused_string: String)
