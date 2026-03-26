package spark3.sql.dataframe.convert

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory


class DfToJsonTest extends AnyFlatSpec with Matchers {

  it should "convert DataFrame to JSON" in {
    val jsonList = Factory.cityListDf.toJSON.collect
    jsonList should contain allOf(
      """{"name":"Moscow","establishYear":1147}""",
      """{"name":"SPb","establishYear":1703}""")
  }

}
