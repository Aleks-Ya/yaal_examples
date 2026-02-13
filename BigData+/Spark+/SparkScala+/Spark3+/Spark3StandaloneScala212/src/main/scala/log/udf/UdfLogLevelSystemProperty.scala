package log.udf

import org.apache.logging.log4j.core.config.Configurator
import org.apache.logging.log4j.{Level, LogManager}

import scala.collection.JavaConverters._
import scala.util.control.NonFatal

trait UdfLogLevelSystemProperty {
  {
    val log = LogManager.getLogger(classOf[UdfLogLevelSystemProperty])
    try {
      log.info("Configuring logger levels...")
      System.getProperties.asScala.foreach { case (key, value) =>
        val prefix = "logger.level."
        if (key.startsWith(prefix)) {
          val loggerName = key.substring(prefix.length)
          try {
            val level = Level.toLevel(value)
            Configurator.setLevel(loggerName, level)
            log.info(s"Set log level: $loggerName=$level")
          } catch {
            case NonFatal(e) => log.error(s"Cannot set log level for: $key", e)
          }
        }
      }
    } catch {
      case NonFatal(e) => log.error("Cannot set log levels", e)
    }
  }
}
