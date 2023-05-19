package log.executor

import org.apache.spark.rdd.RDD

import java.lang.management.ManagementFactory

class WordProcessor extends Serializable {
  @transient private lazy val log = org.apache.log4j.LogManager.getLogger(this.getClass)

  def processRdd(rdd: RDD[String]): RDD[String] = {
    log.info("[Log4J] Process words: " + ManagementFactory.getRuntimeMXBean.getName)
    println("[println] Process words: " + ManagementFactory.getRuntimeMXBean.getName)
    rdd.map(word => word.toUpperCase)
  }
}
