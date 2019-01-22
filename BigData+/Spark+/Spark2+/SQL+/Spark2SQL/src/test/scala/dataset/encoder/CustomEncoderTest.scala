package dataset.encoder

import factory.Factory
import org.apache.spark.sql.{Encoder, Encoders}
import org.scalatest.{FlatSpec, Matchers}

class CustomEncoderTest extends FlatSpec with Matchers {

  it should "Kryo encoder" in {
    val ss = Factory.ss
    import ss.sqlContext.implicits._
    val ds = ss.createDataset(Seq("John", "Mary"))
    ds.show

    class People(val name: String) extends Serializable {}
    implicit val mapEncoder: Encoder[People] = Encoders.kryo[People]
    val peopleDs = ds.map(name => new People(name))
    peopleDs.show
    peopleDs.foreach(println(_))
  }

  //  TODO add
  //  implicit val mapEncoder: Encoder[String] = org.apache.spark.sql.Encoders.STRING
  //  implicit val mapEncoder: Encoder[People] = org.apache.spark.sql.Encoders.kryo[People]
  //  implicit val mapEncoder: Encoder[City] = org.apache.spark.sql.Encoders.product[City]
  //  implicit val mapEncoder: Encoder[City] = org.apache.spark.sql.Encoders.bean[City]

}

