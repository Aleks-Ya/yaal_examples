package dataset.encoder

import factory.Factory
import org.apache.spark.sql.{Dataset, Encoder, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ProductEncoderTest extends AnyFlatSpec with Matchers {

  it should "use Product encoder" in {
    val personJohn = PersonProduct(
      name = "John",
      age = 30,
      time = 1000L,
      male = true,
      metadata = Map("country" -> "USA"),
      contacts = PersonContacts("NYC", "123-456-7890"))

    val personMary = PersonProduct(
      name = "Mary",
      age = 25,
      time = 500L,
      male = false,
      metadata = Map("country" -> "UK"),
      contacts = PersonContacts("London", "234-567-8901"))

    val ss = Factory.ss
    implicit val encoder: Encoder[PersonProduct] = Encoders.product[PersonProduct]
    val ds: Dataset[PersonProduct] = ss.createDataset(Seq(personJohn, personMary))
    ds.collect should contain allOf(personJohn, personMary)
  }

}

case class PersonProduct(
                          name: String,
                          age: Int,
                          time: Long,
                          male: Boolean,
                          metadata: Map[String, String],
                          contacts: PersonContacts
                        )

case class PersonContacts(city: String, phone: String)