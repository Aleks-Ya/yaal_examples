package log.udf

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

object UdfDriverLogic {
  @transient private lazy val log4j1 = org.apache.log4j.LogManager.getLogger(getClass)
  @transient private lazy val log4j2 = org.apache.logging.log4j.LogManager.getLogger(getClass)

  def doWork(ss: SparkSession): Unit = {
    printf("[printf][%s][%s] Driver Logic Print\n", getRuntimeMXBean.getName, currentThread().getName)

    log4j1.info(s"[Log4J1][${getRuntimeMXBean.getName}][${currentThread().getName}] Driver Logic Info")
    log4j1.debug(s"[Log4J1][${getRuntimeMXBean.getName}][${currentThread().getName}] Driver Logic Debug")

    log4j2.info(s"[Log4J2][${getRuntimeMXBean.getName}][${currentThread().getName}] Driver Logic Info")
    log4j2.debug(s"[Log4J2][${getRuntimeMXBean.getName}][${currentThread().getName}] Driver Logic Debug")

    import ss.sqlContext.implicits._
    val str = ss.createDataset(Seq("a", "b", "c", "d"))
      .repartition(2)
      .withColumn("upper", UpperUdf(col("value")))
      .collect()
      .map(row => s"${row.getString(0)}-${row.getString(1)}")
      .sorted
      .mkString(",")

    println(s"Result: $str")
    assert(str.equals("a-A,b-B,c-C,d-D"))
  }
}
