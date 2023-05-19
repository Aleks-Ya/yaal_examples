package log.executor

import java.lang.management.ManagementFactory

object UpperCaseLambda {
  @transient private lazy val log = org.apache.log4j.LogManager.getLogger(this.getClass)

  def toUppercase(word: String): String = {
    printf("[printf][%s][%s] UpperCaseLambda \n", ManagementFactory.getRuntimeMXBean.getName, Thread.currentThread().getName)
    log.info(s"[Log4J][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] UpperCaseLambda")
    word.toUpperCase
  }
}
