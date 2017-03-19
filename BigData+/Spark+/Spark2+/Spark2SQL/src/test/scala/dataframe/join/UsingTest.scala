package dataframe.join

import dataframe.DfFactory
import org.apache.spark.sql.functions.monotonically_increasing_id
import org.scalatest.{FlatSpec, Matchers}

class UsingTest extends FlatSpec with Matchers {

  "Join with USING operation" should "works" in {
    val peopleDf = DfFactory.peopleDf.withColumn("id", monotonically_increasing_id())
    val citiesDf = DfFactory.citiesDf .withColumn("id", monotonically_increasing_id())
    val joined = peopleDf.join(citiesDf, "id")
    joined.show
    joined.printSchema()
  }
}