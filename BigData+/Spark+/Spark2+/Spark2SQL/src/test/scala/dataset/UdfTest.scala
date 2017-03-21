package dataset

import factory.Factory
import org.apache.spark.sql.functions._
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class UdfTest extends FlatSpec with BeforeAndAfterAll with Matchers {

  it should "init dataset" in {
    val sqlContext = Factory.ss.sqlContext
    import sqlContext.implicits._
    val ds = Factory.ss.createDataset(Seq("a", "b"))
    ds.show
    val upper: String => String = _.toUpperCase
    val upperUDF = udf(upper)
    ds.withColumn("upper", upperUDF('value)).show
  }

  it should "put sum of an array to new column" in {
    val sqlContext = Factory.ss.sqlContext
    import sqlContext.implicits._
    val ds = Factory.ss.createDataset(Seq(Array(1, 2, 3), Array(4, 5, 6)))
    ds.show
    val sum: Seq[Int] => Int = _.sum
    sum(Array(1, 2, 3)) shouldEqual 6
    val sumUdf = udf(sum)
    val newDs = ds.withColumn("sum", sumUdf('value))
    newDs.show
    newDs.select(col("sum").as[Int]).collectAsList should contain inOrderOnly(6, 15)
  }

  it should "pass additional params to UDF" in {
    val sqlContext = Factory.ss.sqlContext
    import sqlContext.implicits._
    val ds = Factory.ss.createDataset(Seq("a", "b"))
    ds.show
    val upperSuffix: String => String => String = suffix => string => (string + suffix).toUpperCase
    val upperUDF = udf(upperSuffix("_suf"))
    ds.withColumn("upper", upperUDF('value)).show
  }
}