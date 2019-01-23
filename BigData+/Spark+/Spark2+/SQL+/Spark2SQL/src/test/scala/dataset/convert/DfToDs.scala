package dataset.convert

import factory.Factory
import org.apache.spark.sql.{Encoder, Encoders}
import org.scalatest.{FlatSpec, Matchers}

class DfToDs extends FlatSpec with Matchers {


  it should "dataframe.convert DataFrame to Dataset" in {
    implicit val mapEncoder: Encoder[City1] = Encoders.product[City1]
    val ds = Factory.cityListDf.as[City1]
    ds.show
  }
}

/* Can't be an inner class*/
private case class City1(city: String)
