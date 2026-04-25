package spark3.sql.dataset.operation.transformation

import org.apache.spark.sql.Dataset
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{City, Factory, SparkMatchers}

class SelectTupleTest extends AnyFlatSpec with SparkMatchers {

  it should "select tuples" in {
    import Factory.ss.implicits._
    val ds: Dataset[City] = Factory.cityDs
    val tupleDs: Dataset[(String, Int)] = ds.map(city => (city.name, city.establishYear))
    val resultDs = tupleDs.select('_1 as "city_name", '_2 as "city_year")
    resultDs shouldContain(
      """{"city_name":"Moscow","city_year":1147}""",
      """{"city_name":"SPb","city_year":1703}""",
      """{"city_name":"New York","city_year":1665}"""
    )
  }

}
