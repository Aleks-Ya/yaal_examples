package dataset.encoder

import factory.Factory
import org.apache.spark.sql.{Encoder, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class KryoEncoderTest extends AnyFlatSpec with Matchers {

  it should "Kryo encoder" in {
    val ss = Factory.ss
    implicit val encoder: Encoder[PersonKryo] = Encoders.kryo[PersonKryo]
    val ds = ss.createDataset(Seq(PersonKryo("John"), PersonKryo("Mary")))
    ds.show
  }

}

case class PersonKryo(name: String)
