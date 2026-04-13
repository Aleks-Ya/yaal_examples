package spark3.sql.dataset.operation.transformation

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.{City, Factory}

class MapTransformationTest extends AnyFlatSpec with Matchers {

  it should "modify values" in {
    import Factory.ss.implicits._
    val ds = Factory.cityDs.map(city => City(city.name.toUpperCase, city.establishYear))
    ds.toJSON.collect should contain inOrderOnly(
      """{"name":"MOSCOW","establishYear":1147}""",
      """{"name":"SPB","establishYear":1703}""",
      """{"name":"NEW YORK","establishYear":1665}"""
    )
  }

}
