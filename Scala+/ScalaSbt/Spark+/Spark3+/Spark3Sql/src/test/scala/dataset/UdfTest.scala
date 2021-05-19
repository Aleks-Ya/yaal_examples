package dataset

import factory.Factory
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.JavaConverters._
import scala.collection.mutable


class UdfTest extends AnyFlatSpec with Matchers {

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
    val dsSum: Dataset[Int] = newDs.select(col("sum").as[Int])
    val list: mutable.Buffer[Int] = dsSum.collectAsList().asScala
    list should contain inOrderOnly(6, 15)
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