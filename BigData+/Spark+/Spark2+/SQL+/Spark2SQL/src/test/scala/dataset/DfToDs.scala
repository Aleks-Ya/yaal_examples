package dataset

import factory.Factory
import org.apache.spark.sql.{Encoder, Encoders}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class DfToDs extends FlatSpec with BeforeAndAfterAll with Matchers {


  it should "convert DataFrame to Dataset" in {
    implicit val mapEncoder: Encoder[City1] = Encoders.product[City1]
    val ds = Factory.citiesDf.as[City1]
    ds.show
  }
}

/* Can't be an inner class*/
private case class City1(city: String)
