package spark3.sql.dataframe.transformation.union

import org.apache.spark.sql.{AnalysisException, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.sql.Factory.createDf


class UnionByNameTransformationTest extends AnyFlatSpec with Matchers {

  it should "unite different column order (auto-reorder)" in {
    val df1 = createDf("id STRING, name STRING", Row("1", "John"), Row("2", "Mary"))
    val df2 = createDf("name STRING, id STRING", Row("Mark", "3"), Row("Mike", "4"))
    val unionDf = df1.unionByName(df2)
    unionDf.toJSON.collect should contain inOrderOnly(
      """{"id":"1","name":"John"}""",
      """{"id":"2","name":"Mary"}""",
      """{"id":"3","name":"Mark"}""",
      """{"id":"4","name":"Mike"}"""
    )
  }

  it should "unite a struct column" in {
    val df1 = createDf("id STRING, details STRUCT<name:STRING, age:INT>", Row("1", Row("John", 30)), Row("2", Row("Mary", 25)))
    val df2 = createDf("details STRUCT<age:INT, name:STRING>, id STRING", Row(Row(20, "Mark"), "3"))
    val unionDf = df1.unionByName(df2)
    unionDf.toJSON.collect should contain inOrderOnly(
      """{"id":"1","details":{"name":"John","age":30}}""",
      """{"id":"2","details":{"name":"Mary","age":25}}""",
      """{"id":"3","details":{"name":"Mark","age":20}}"""
    )
  }

  it should "not allow missing columns" in {
    val df1 = createDf("id STRING, name STRING", Row("1", "John"), Row("2", "Mary"))
    val df2 = createDf("id STRING, person STRING", Row("3", "Mark"), Row("4", "Mike"))
    val e = intercept[AnalysisException] {
      df1.unionByName(df2)
    }
    e.getMessage should include("""Cannot resolve column name "name" among (id, person).""")
  }

  it should "allow missing columns" in {
    val df1 = createDf("id STRING, name STRING", Row("1", "John"), Row("2", "Mary"))
    val df2 = createDf("id STRING, person STRING", Row("3", "Mark"), Row("4", "Mike"))
    val unionDf = df1.unionByName(df2, allowMissingColumns = true)
    unionDf.toJSON.collect should contain inOrderOnly(
      """{"id":"1","name":"John","person":null}""",
      """{"id":"2","name":"Mary","person":null}""",
      """{"id":"3","name":null,"person":"Mark"}""",
      """{"id":"4","name":null,"person":"Mike"}"""
    )
  }

}