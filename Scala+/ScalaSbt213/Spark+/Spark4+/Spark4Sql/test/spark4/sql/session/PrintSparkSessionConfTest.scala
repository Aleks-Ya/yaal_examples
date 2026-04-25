package spark4.sql.session

import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class PrintSparkSessionConfTest extends AnyFlatSpec with SparkMatchers {
  it should "print config of Spark Session" in {
    Factory.ss.conf.getAll.foreach(println)
  }
}
