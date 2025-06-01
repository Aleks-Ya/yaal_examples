package dataset.convert

import factory.Factory
import org.apache.spark.sql.{Encoder, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DfToDsTest extends AnyFlatSpec with Matchers {
  it should "dataframe.convert DataFrame to Dataset" in {
    implicit val mapEncoder: Encoder[City1] = Encoders.product[City1]
    val ds = Factory.cityListDf.as[City1]
    ds.show
  }
}

/* Can't be an inner class*/
private case class City1(city: String)
