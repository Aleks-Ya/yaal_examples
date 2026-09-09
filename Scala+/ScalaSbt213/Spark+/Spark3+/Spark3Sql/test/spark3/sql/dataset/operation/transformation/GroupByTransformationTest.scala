package spark3.sql.dataset.operation.transformation

import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema
import org.apache.spark.sql.{DataFrame, Dataset, Encoder, Encoders}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{City, Factory, SparkMatchers}

class GroupByTransformationTest extends AnyFlatSpec with SparkMatchers {
  private val cities = Seq(City("Moscow", 1234), City("Moscow", 7890), City("SPb", 4567))

  it should "group by key" in {
    implicit val mapEncoder: Encoder[String] = Encoders.STRING
    val ds: Dataset[City] = Factory.createCityDs(cities)
    val updatedDs: Dataset[String] = ds.groupByKey(city => city.name)
      .mapGroups((cityName, cities) => {
        val yearSum = cities.map(city => city.establishYear).sum
        s"$cityName - $yearSum"
      })
    updatedDs shouldContain(
      """Moscow - 9124""",
      """SPb - 4567""")
  }

  it should "group by and show count" in {
    val ds: Dataset[City] = Factory.createCityDs(cities)
    val updatedDf: DataFrame = ds.groupBy("name").count()
    updatedDf shouldContain(
      """{"name":"Moscow","count":2}""",
      """{"name":"SPb","count":1}""")
  }

  it should "group by with collect_list" in {
    implicit val mapEncoder: Encoder[String] = Encoders.STRING
    import org.apache.spark.sql.functions.{collect_list, struct}

    val ds: Dataset[City] = Factory.createCityDs(cities)
    val updatedDs: Dataset[String] = ds.groupBy("name")
      .agg(collect_list(struct("name", "establishYear")).as("data"))
      .map(data => {
        val cityName = data.getString(0)
        val rows = data.getSeq(1).asInstanceOf[Seq[GenericRowWithSchema]]
        val yearSum = rows.map(row => row.getInt(1)).sum
        s"$cityName - $yearSum"
      })
    updatedDs shouldContain(
      """Moscow - 9124""",
      """SPb - 4567""")
  }
}
