package dataframe.create

import factory.Factory
import org.scalatest.{FlatSpec, Matchers}

class DsToDf extends FlatSpec with Matchers {

  it should "dataframe.convert Dataset to DataFrame" in {
    val df = Factory.cityDs.toDF()
    df.show
    df.columns should contain allOf("name", "establishYear")
  }
}