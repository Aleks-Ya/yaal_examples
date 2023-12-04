package log.executor

import java.lang.Thread.currentThread
import java.lang.management.ManagementFactory.getRuntimeMXBean

object UpperCaseLambda extends LogLevelSystemProperty {
  @transient private lazy val log4j1 = org.apache.log4j.LogManager.getLogger(getClass)
  @transient private lazy val log4j2 = org.apache.logging.log4j.LogManager.getLogger(getClass)
  configureLoggerLevels()

  def toUppercase(word: String): String = {
    printf("[printf][%s][%s] Executor UpperCaseLambda Print '%s' \n", getRuntimeMXBean.getName, currentThread().getName, word)
    log4j1.info(s"[Log4J1][${getRuntimeMXBean.getName}][${currentThread().getName}] Executor UpperCaseLambda Info '$word'")
    log4j2.info(s"[Log4J2][${getRuntimeMXBean.getName}][${currentThread().getName}] Executor UpperCaseLambda Info '$word'")
    log4j1.debug(s"[Log4J1][${getRuntimeMXBean.getName}][${currentThread().getName}] Executor UpperCaseLambda Debug '$word'")
    log4j2.debug(s"[Log4J2][${getRuntimeMXBean.getName}][${currentThread().getName}] Executor UpperCaseLambda Debug '$word'")
    word.toUpperCase
  }
}
