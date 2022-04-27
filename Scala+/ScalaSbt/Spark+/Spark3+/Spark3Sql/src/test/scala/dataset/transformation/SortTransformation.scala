package dataset.transformation

import factory.Factory
import org.apache.spark.sql.functions.{asc, desc}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SortTransformation extends AnyFlatSpec with Matchers {

  it should "sort a Dataset ASC (default)" in {
    val ds = Factory.cityDs.sort("establishYear")
    ds.toJSON.collect() should contain inOrderOnly(
      """{"name":"Moscow","establishYear":1147}""",
      """{"name":"New York","establishYear":1665}""",
      """{"name":"SPb","establishYear":1703}"""
    )
  }

  it should "sort a Dataset ASC (explicitly)" in {
    val ds = Factory.cityDs.sort(asc("establishYear"))
    ds.toJSON.collect() should contain inOrderOnly(
      """{"name":"Moscow","establishYear":1147}""",
      """{"name":"New York","establishYear":1665}""",
      """{"name":"SPb","establishYear":1703}"""
    )
  }

  it should "sort a Dataset DESC (default)" in {
    val ds = Factory.cityDs.sort(desc("establishYear"))
    ds.toJSON.collect() should contain inOrderOnly(
      """{"name":"SPb","establishYear":1703}""",
      """{"name":"New York","establishYear":1665}""",
      """{"name":"Moscow","establishYear":1147}"""
    )
  }

}
