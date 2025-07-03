package dataset.convert

import factory.Factory
import org.apache.spark.sql.{Encoder, Encoders, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DfToDsTest extends AnyFlatSpec with Matchers {

  it should "dataframe.convert DataFrame to Dataset" in {
    implicit val mapEncoder: Encoder[City1] = Encoders.product[City1]
    val ds = Factory.cityListDf.as[City1]
    ds.schema.toDDL shouldEqual "city STRING"
    ds.collect should contain allOf(City1("Moscow"), City1("SPb"))
  }

  it should "convert all data types" in {
    val ddl = "string STRING,integer INT"
    val df = Factory.createDf(ddl,
      Row("s1", 10),
      Row("s2", 20))
    implicit val mapEncoder: Encoder[AllDataTypes] = Encoders.product[AllDataTypes]
    val ds = df.as[AllDataTypes]
    ds.schema.toDDL shouldEqual ddl
    ds.collect should contain allOf(AllDataTypes("s1", 10), AllDataTypes("s2", 20))
  }

}

/* Can't be an inner class*/
private case class City1(city: String)

private case class AllDataTypes(string: String, integer: Int)