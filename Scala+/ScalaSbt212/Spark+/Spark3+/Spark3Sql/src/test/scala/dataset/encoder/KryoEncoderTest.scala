package dataset.encoder

import factory.Factory
import org.apache.spark.sql.{Dataset, Encoder, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class KryoEncoderTest extends AnyFlatSpec with Matchers {

  it should "Kryo encoder" in {
    val john = PersonKryo("John")
    val mary = PersonKryo("Mary")

    val ss = Factory.ss
    implicit val encoder: Encoder[PersonKryo] = Encoders.kryo[PersonKryo]
    val ds: Dataset[PersonKryo] = ss.createDataset(Seq(john, mary))
    val array: Array[PersonKryo] = ds.collect
    array should contain allOf(john, mary)
  }

}

case class PersonKryo(name: String)
