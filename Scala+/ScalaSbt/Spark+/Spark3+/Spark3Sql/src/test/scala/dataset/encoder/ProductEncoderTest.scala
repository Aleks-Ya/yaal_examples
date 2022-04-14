package dataset.encoder

import factory.Factory
import org.apache.spark.sql.{Encoder, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ProductEncoderTest extends AnyFlatSpec with Matchers {

  it should "use Product encoder" in {
    val ss = Factory.ss
    implicit val encoder: Encoder[PersonProduct] = Encoders.product[PersonProduct]
    val ds = ss.createDataset(Seq(PersonProduct("John"), PersonProduct("Mary")))
    ds.show
  }

}

case class PersonProduct(name: String)
