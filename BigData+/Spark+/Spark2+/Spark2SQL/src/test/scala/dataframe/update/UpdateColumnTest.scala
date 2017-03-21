package dataframe.update

import factory.Factory
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.ByteType
import org.scalatest.{FlatSpec, Matchers}

class UpdateColumnTest extends FlatSpec with Matchers {

  it should "multiply column value" in {
    val df = Factory.peopleDf
    df.show
    df.withColumn("age", col("age").multiply(2)).show
  }

}