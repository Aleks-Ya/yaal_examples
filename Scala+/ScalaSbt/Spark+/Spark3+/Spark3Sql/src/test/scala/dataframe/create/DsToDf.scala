package dataframe.create

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class DsToDf extends AnyFlatSpec with Matchers {

  it should "dataframe.convert Dataset to DataFrame" in {
    val df = Factory.cityDs.toDF()
    df.show
    df.columns should contain allOf("name", "establishYear")
  }
}