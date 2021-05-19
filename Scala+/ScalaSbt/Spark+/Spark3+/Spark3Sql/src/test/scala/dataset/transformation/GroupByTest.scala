package dataset.transformation

import factory.{City, Factory}
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema
import org.apache.spark.sql.{Encoder, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GroupByTest extends AnyFlatSpec with Matchers {

  private val cities = Seq(City("Moscow", 1234), City("Moscow", 7890), City("SPb", 4567))

  it should "group by key" in {
    implicit val mapEncoder: Encoder[String] = Encoders.STRING
    val ds = Factory.createCityDs(cities)
    ds.show
    implicit val encoder: Encoder[City] = Encoders.product[City]
    ds
      .groupByKey(city => city.name)
      .mapGroups((cityName, cities) => {
        val yearSum = cities.map(city => city.establishYear).sum
        s"$cityName - $yearSum"
      })
      .foreach(text => println(text))
  }

  it should "group by and show count" in {
    val ds = Factory.createCityDs(cities)
    ds.show
    val groupedDs = ds.groupBy("name").count()
    groupedDs.show
    groupedDs.columns should contain allOf("name", "count")
  }

  it should "group by with collect_list" in {
    implicit val mapEncoder: Encoder[String] = Encoders.STRING
    import org.apache.spark.sql.functions.{collect_list, struct}

    val ds = Factory.createCityDs(cities)
    ds.show
    ds
      .groupBy("name")
      .agg(collect_list(struct("name", "establishYear")).as("data"))
      .map(data => {
        val cityName = data.getString(0)
        val rows = data.getSeq(1).asInstanceOf[Seq[GenericRowWithSchema]]
        val yearSum = rows.map(row => row.getInt(1)).sum
        s"$cityName - $yearSum"
      })
      .foreach(text => println(text))
  }


}
