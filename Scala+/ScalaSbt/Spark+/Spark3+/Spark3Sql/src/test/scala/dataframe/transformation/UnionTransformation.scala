package dataframe.transformation

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class UnionTransformation extends AnyFlatSpec with Matchers {
  it should "unite two DataFrames" in {
    import Factory.ss.sqlContext.implicits._
    val df1 = (1 to 2).toDF("numbers")
    val df2 = (5 to 6).toDF("numbers")
    val unionDf = df1.union(df2)

    unionDf.toJSON.collect() should contain inOrderOnly(
      """{"numbers":1}""",
      """{"numbers":2}""",
      """{"numbers":5}""",
      """{"numbers":6}"""
    )
  }
}