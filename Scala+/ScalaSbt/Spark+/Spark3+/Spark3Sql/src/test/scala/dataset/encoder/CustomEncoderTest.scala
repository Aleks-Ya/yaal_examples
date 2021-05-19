package dataset.encoder

import factory.Factory
import org.apache.spark.sql.{Encoder, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.time.LocalDateTime
import java.util.Date

class CustomEncoderTest extends AnyFlatSpec with Matchers {

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

  it should "encoder for java.util.Date" in {
    val ss = Factory.ss
    implicit val mapEncoder: Class[Date] => Encoder[Date] = Encoders.bean[Date]
    val dates = Seq(new Date())
    // not work
    //    val ds = ss.createDataset(dates)
    //    ds.show
  }

  it should "encoder for java.util.Date 2" in {
    val ss = Factory.ss
    implicit val mapEncoder: Class[LocalDateTime] => Encoder[LocalDateTime] = Encoders.bean[LocalDateTime]
    val dates = Seq(LocalDateTime.now)
    // not work
    //    val ds = ss.createDataset(dates)
    //    ds.show
  }

  //  TODO add
  //  implicit val mapEncoder: Encoder[String] = org.apache.spark.sql.Encoders.STRING
  //  implicit val mapEncoder: Encoder[People] = org.apache.spark.sql.Encoders.kryo[People]
  //  implicit val mapEncoder: Encoder[City] = org.apache.spark.sql.Encoders.product[City]
  //  implicit val mapEncoder: Encoder[City] = org.apache.spark.sql.Encoders.bean[City]
  //  implicit val decimalEncoder: Encoder[java.math.BigDecimal] = Encoders.DECIMAL
  //  implicit val bigDecimalEncoder: Encoder[BigDecimal] = Encoders.product[BigDecimal]

}

