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
}