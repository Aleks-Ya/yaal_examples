package log.executor

import java.lang.management.ManagementFactory

object UpperCaseLambda {
  @transient private lazy val log4j1 = org.apache.log4j.LogManager.getLogger(getClass)
  @transient private lazy val log4j2 = org.apache.logging.log4j.LogManager.getLogger(getClass)

  def toUppercase(word: String): String = {
    printf("[printf][%s][%s] UpperCaseLambda '%s' \n", ManagementFactory.getRuntimeMXBean.getName, Thread.currentThread().getName, word)
    log4j1.info(s"[Log4J1][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] UpperCaseLambda '$word'")
    log4j2.info(s"[Log4J2][${ManagementFactory.getRuntimeMXBean.getName}][${Thread.currentThread().getName}] UpperCaseLambda '$word'")
    word.toUpperCase
  }
}
