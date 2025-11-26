package dataset.convert

import factory.{City, Factory}
import org.apache.spark.sql.{Dataset, Encoder, Encoders}
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.write
import org.json4s.{Formats, NoTypeHints}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DsRowToJsonTest extends AnyFlatSpec with Matchers {

  it should "dataframe.convert whole Dataset to JSON" in {
    val cityDs: Dataset[City] = Factory.cityDs
    val jsonDs: Dataset[String] = cityDs.toJSON
    val jsonList: Array[String] = jsonDs.collect
    jsonList should contain inOrderOnly(
      """{"name":"Moscow","establishYear":1147}""",
      """{"name":"SPb","establishYear":1703}""",
      """{"name":"New York","establishYear":1665}"""
    )
  }

  it should "dataframe.convert Dataset row to JSON" in {
    implicit val encoder: Encoder[String] = Encoders.STRING

    val cityDs: Dataset[City] = Factory.cityDs
    val jsonDs: Dataset[String] = cityDs.map(city => {
      implicit val formats: AnyRef with Formats = Serialization.formats(NoTypeHints)
      val name = city.name
      val json = write(city)
      s"$name - $json}"
    })
    val jsonList: Array[String] = jsonDs.collect()

    jsonList should contain inOrderOnly(
      """Moscow - {"name":"Moscow","establishYear":1147}}""",
      """SPb - {"name":"SPb","establishYear":1703}}""",
      """New York - {"name":"New York","establishYear":1665}}"""
    )
  }
}
