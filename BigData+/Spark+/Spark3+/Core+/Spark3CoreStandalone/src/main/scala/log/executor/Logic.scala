package log.executor

import org.apache.log4j.LogManager
import org.apache.spark.{SparkConf, SparkContext}

import java.lang.management.ManagementFactory

object Logic {
  @transient private lazy val log = LogManager.getLogger(this.getClass)

  def doWork(conf: SparkConf): Unit = {
    printf("[printf][%s][%s] Logic\n", ManagementFactory.getRuntimeMXBean.getName, Thread.currentThread().getName)
    log.info(s"[Log4J][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] Logic")

    val sc = new SparkContext(conf)
    val rdd = sc.parallelize(Seq("Hello, ", "World", "!"))
    val rdd2 = rdd.map(UpperCaseLambda.toUppercase)
    val actSet = rdd2.collect.toSet
    sc.stop()
    assert(actSet.equals(Set("HELLO, ", "WORLD", "!")))
  }
}
