package dataframe.convert

import factory.Factory
import org.scalatest.{FlatSpec, Matchers}

class DfToJsonTest extends FlatSpec with Matchers {

  it should "dataframe.convert whole DataFrame to JSON" in {
    val jsonList = Factory.cityListDf.toJSON.collect()
    jsonList should contain allOf(
      """{"name":"Moscow","establishYear":1147}""",
      """{"name":"SPb","establishYear":1703}""")
  }

}
