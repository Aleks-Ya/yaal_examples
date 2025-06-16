package dataframe.convert

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class DfToJsonTest extends AnyFlatSpec with Matchers {

  it should "convert DataFrame to JSON" in {
    val jsonList = Factory.cityListDf.toJSON.collect
    jsonList should contain allOf(
      """{"name":"Moscow","establishYear":1147}""",
      """{"name":"SPb","establishYear":1703}""")
  }

}
