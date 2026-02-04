package dataframe.transformation.union

import factory.Factory
import org.apache.spark.sql.Row
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class UnionByNameTransformationTest extends AnyFlatSpec with Matchers {
  it should "unite different column order (auto-reorder)" in {
    val df1 = Factory.createDf("id STRING, name STRING", Row("1", "John"), Row("2", "Mary"))
    val df2 = Factory.createDf("name STRING, id STRING", Row("Mark", "3"), Row("Mike", "4"))
    val unionDf = df1.unionByName(df2)
    unionDf.toJSON.collect should contain inOrderOnly(
      """{"id":"1","name":"John"}""",
      """{"id":"2","name":"Mary"}""",
      """{"id":"3","name":"Mark"}""",
      """{"id":"4","name":"Mike"}"""
    )
  }
}