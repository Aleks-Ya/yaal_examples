package spark3.sql.dataset.operation.transformation

import org.apache.spark.sql.functions.{asc, desc}
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{City, Factory, SparkMatchers}

class SortTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "sort a Dataset ASC (default)" in {
    val ds = Factory.cityDs.sort("establishYear")
    ds shouldContain(
      City("Moscow", 1147),
      City("New York", 1665),
      City("SPb", 1703)
    )
  }

  it should "sort a Dataset ASC (explicitly)" in {
    val ds = Factory.cityDs.sort(asc("establishYear"))
    ds shouldContain(
      City("Moscow", 1147),
      City("New York", 1665),
      City("SPb", 1703)
    )
  }

  it should "sort a Dataset DESC (default)" in {
    val ds = Factory.cityDs.sort(desc("establishYear"))
    ds shouldContain(
      City("SPb", 1703),
      City("New York", 1665),
      City("Moscow", 1147)
    )
  }

}
