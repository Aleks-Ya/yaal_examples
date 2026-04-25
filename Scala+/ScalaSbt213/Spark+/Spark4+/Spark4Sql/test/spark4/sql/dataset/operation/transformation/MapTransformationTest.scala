package spark4.sql.dataset.operation.transformation

import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{City, Factory, SparkMatchers}

class MapTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "modify values" in {
    import Factory.ss.implicits._
    val ds = Factory.cityDs.map(city => City(city.name.toUpperCase, city.establishYear))
    ds shouldContain(
      City("MOSCOW", 1147),
      City("SPB", 1703),
      City("NEW YORK", 1665)
    )
  }

}
