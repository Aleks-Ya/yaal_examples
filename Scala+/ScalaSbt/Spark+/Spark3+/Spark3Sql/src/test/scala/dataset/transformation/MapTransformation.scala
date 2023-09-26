package dataset.transformation

import factory.{City, Factory}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MapTransformation extends AnyFlatSpec with Matchers {

  it should "modify values" in {
    import Factory.ss.sqlContext.implicits._
    val ds = Factory.cityDs.map(city => City(city.name.toUpperCase, city.establishYear))
    ds.toJSON.collect() should contain inOrderOnly(
      """{"name":"MOSCOW","establishYear":1147}""",
      """{"name":"SPB","establishYear":1703}""",
      """{"name":"NEW YORK","establishYear":1665}"""
    )
  }

}
