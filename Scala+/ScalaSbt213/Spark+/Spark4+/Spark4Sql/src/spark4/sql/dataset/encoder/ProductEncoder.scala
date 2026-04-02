package spark4.sql.dataset.encoder

import java.time.ZonedDateTime

case class PersonProduct(
                          name: String,
                          age: Int,
                          time: Long,
                          male: Boolean,
                          metadata: Map[String, String],
                          contacts: ContactsProduct
                        )

case class ContactsProduct(city: String, phone: String)

case class UnsupportedProduct(zonedDateTime: ZonedDateTime)
