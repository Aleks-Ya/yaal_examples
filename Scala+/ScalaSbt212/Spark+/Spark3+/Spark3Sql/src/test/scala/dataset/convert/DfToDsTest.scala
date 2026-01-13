package dataset.convert

import factory.Factory
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Encoders, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DfToDsTest extends AnyFlatSpec with Matchers {

  it should "convert DataFrame to Dataset" in {
    val df: DataFrame = Factory.cityListDf
    implicit val encoder: Encoder[City1] = Encoders.product[City1]
    val ds: Dataset[City1] = df.as[City1]
    ds.schema.toDDL shouldEqual "city STRING"
    ds.collect should contain allOf(City1("Moscow"), City1("SPb"))
  }

  it should "convert all data types" in {
    val ddl = "string STRING,integer INT"
    val df: DataFrame = Factory.createDf(ddl,
      Row("s1", 10),
      Row("s2", 20))
    implicit val encoder: Encoder[AllDataTypes] = Encoders.product[AllDataTypes]
    val ds: Dataset[AllDataTypes] = df.as[AllDataTypes]
    ds.schema.toDDL shouldEqual ddl
    ds.collect should contain allOf(AllDataTypes("s1", 10), AllDataTypes("s2", 20))
  }

  it should "convert DataFrame to Dataset having custom classes" in {
    val df: DataFrame = Factory.createDf("name STRING,text STRING",
      Row("name1", "prefix1,suffix1"),
      Row("name2", "prefix2,suffix2"))
    implicit val encoder: Encoder[OuterType] = Encoders.product[OuterType]
    val ds: Dataset[OuterType] = df.map { r =>
      val name = r.getString(0)
      val text = r.getString(1)
      OuterType(name, NestedType(text))
    }
    ds.collect should contain allOf(
      OuterType("name1", NestedType("prefix1", "suffix1")),
      OuterType("name2", NestedType("prefix2", "suffix2"))
    )
  }

}

/* Can't be an inner class*/
private case class City1(city: String)

private case class AllDataTypes(string: String, integer: Int)

private case class OuterType(name: String, nested: NestedType)

private case class NestedType(prefix: String, suffix: String)

private object NestedType {
  def apply(text: String): NestedType = {
    val split = text.split(",")
    val prefix = split(0)
    val suffix = split(1)
    new NestedType(prefix, suffix)
  }
}