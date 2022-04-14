package dataset.encoder

import factory.Factory
import org.apache.spark.sql.{Encoder, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Bean Encoder can handle only Java classes (not Scala classes).
 */
class BeanEncoderTest extends AnyFlatSpec with Matchers {

  it should "encode a Java class" in {
    val ss = Factory.ss
    implicit val encoder: Encoder[PersonJava] = Encoders.bean(classOf[PersonJava])
    val persons = Seq(new PersonJava(1, "John"), new PersonJava(2, "Mary"))
    val ds = ss.createDataset(persons)
    ds.show
  }

  it should "encode a Scala case class (not work)" in {
    val ss = Factory.ss
    implicit val encoder: Encoder[PersonScala] = Encoders.bean(classOf[PersonScala])
    val persons = Seq(PersonScala(1, "John"), PersonScala(2, "Mary"))
    val ds = ss.createDataset(persons)
    ds.show
  }

}

case class PersonScala(id: Int, name: String)
