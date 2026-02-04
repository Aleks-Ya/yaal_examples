package dataset.encoder

import factory.Factory
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.apache.spark.sql.{Dataset, Encoder}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExpressionEncoderTest extends AnyFlatSpec with Matchers {

  it should "use Expression encoder" in {
    implicit val encoder: Encoder[PersonProduct2] = ExpressionEncoder[PersonProduct2]
    val personJohn = PersonProduct2(
      name = "John",
      age = 30,
      time = 1000L,
      male = true,
      metadata = Map("country" -> "USA"),
      contacts = PersonContacts2("NYC", "123-456-7890"))

    val personMary = PersonProduct2(
      name = "Mary",
      age = 25,
      time = 500L,
      male = false,
      metadata = Map("country" -> "UK"),
      contacts = PersonContacts2("London", "234-567-8901"))

    val ss = Factory.ss
    val ds: Dataset[PersonProduct2] = ss.createDataset(Seq(personJohn, personMary))
    ds.collect should contain allOf(personJohn, personMary)
  }

}

case class PersonProduct2(
                          name: String,
                          age: Int,
                          time: Long,
                          male: Boolean,
                          metadata: Map[String, String],
                          contacts: PersonContacts2
                        )

case class PersonContacts2(city: String, phone: String)