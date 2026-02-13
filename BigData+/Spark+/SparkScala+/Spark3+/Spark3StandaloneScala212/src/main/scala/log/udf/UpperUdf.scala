package log.udf

import org.apache.spark.sql.Column
import org.apache.spark.sql.functions._

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

object UpperUdf extends Serializable with UdfLogLevelSystemProperty {
  @transient private lazy val log4j1 = org.apache.log4j.LogManager.getLogger(getClass)
  @transient private lazy val log4j2 = org.apache.logging.log4j.LogManager.getLogger(getClass)

  def apply(text: Column): Column = udf(upper _).apply(text)

  private def upper(text: String): String = {
    println(s"[printf][${getRuntimeMXBean.getName}][${currentThread().getName}] UDF Print")

    log4j1.info(s"[Log4J1][${getRuntimeMXBean.getName}][${currentThread().getName}] UDF Info")
    log4j1.debug(s"[Log4J1][${getRuntimeMXBean.getName}][${currentThread().getName}] UDF Debug")

    log4j2.info(s"[Log4J2][${getRuntimeMXBean.getName}][${currentThread().getName}] UDF Info")
    log4j2.debug(s"[Log4J2][${getRuntimeMXBean.getName}][${currentThread().getName}] UDF Debug")

    text.toUpperCase
  }
}
