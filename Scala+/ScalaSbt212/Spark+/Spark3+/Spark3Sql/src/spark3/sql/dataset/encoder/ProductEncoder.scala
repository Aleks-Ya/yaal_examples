package spark3.sql.dataset.encoder

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

case class EncoderToSchema(name: String,
                           title: Option[String],
                           age: Int,
                           year: Integer,
                           time: Long,
                           duration: java.lang.Long,
                           male: Boolean,
                           female: java.lang.Boolean)
