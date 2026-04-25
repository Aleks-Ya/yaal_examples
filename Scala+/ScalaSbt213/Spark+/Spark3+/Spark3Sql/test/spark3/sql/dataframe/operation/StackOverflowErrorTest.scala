package spark3.sql.dataframe.operation

import org.apache.spark.sql.functions.col
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

class StackOverflowErrorTest extends AnyFlatSpec with SparkMatchers {

  it should "throw a StackOverflowError 1" in {
    val df = Factory.createDf("x INT")
    val deepExpr = (1 to 100000).foldLeft(col("x")) { (c, _) => c + 1 }
    an[StackOverflowError] should be thrownBy df.select(deepExpr)
  }

  ignore should "throw a StackOverflowError on large number of columns" in {
    var df = Factory.createDf("col_0 INT")
    for (i <- 1 to 3000) {
      df = df.withColumn(s"col_$i", col(s"col_${i - 1}") + i)
    }
  }

}