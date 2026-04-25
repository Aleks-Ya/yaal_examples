package spark3.sql.dataset.encoder

import org.apache.spark.sql.{Dataset, Encoder, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

import java.time.ZonedDateTime

class ProductEncoderTest extends AnyFlatSpec with SparkMatchers {

  it should "use Product encoder" in {
    val personJohn = PersonProduct(
      name = "John",
      age = 30,
      time = 1000L,
      male = true,
      metadata = Map("country" -> "USA"),
      contacts = ContactsProduct("NYC", "123-456-7890"))

    val personMary = PersonProduct(
      name = "Mary",
      age = 25,
      time = 500L,
      male = false,
      metadata = Map("country" -> "UK"),
      contacts = ContactsProduct("London", "234-567-8901"))

    val ss = Factory.ss
    implicit val encoder: Encoder[PersonProduct] = Encoders.product[PersonProduct]
    val ds: Dataset[PersonProduct] = ss.createDataset(Seq(personJohn, personMary))
    ds shouldContain(personJohn, personMary)
  }

  ignore should "use Product encoder with unsupported classes" in {
    implicit val zonedDateTimeEncoder: Encoder[ZonedDateTime] = Encoders.kryo[ZonedDateTime]
    implicit val encoder: Encoder[UnsupportedProduct] = Encoders.product[UnsupportedProduct]
    val person = UnsupportedProduct(ZonedDateTime.now())
    val ds: Dataset[UnsupportedProduct] = Factory.ss.createDataset(Seq(person))
    ds.collect should contain only person
  }

}
