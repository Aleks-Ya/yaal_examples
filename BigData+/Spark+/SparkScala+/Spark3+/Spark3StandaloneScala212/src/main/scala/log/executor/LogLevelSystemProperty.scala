package log.executor

import org.apache.logging.log4j.core.config.Configurator
import org.apache.logging.log4j.{Level, LogManager}

import scala.collection.JavaConverters._

trait LogLevelSystemProperty {
  @transient private val log = LogManager.getLogger(getClass)

  protected def configureLoggerLevels(): Unit =
    try {
      val prefix = "logger.level."
      System.getProperties.asScala.toList
        .filter(property => property._1.startsWith(prefix))
        .foreach { property =>
          try {
            val logger = LogManager.getLogger(property._1.substring(prefix.length))
            val level = Level.toLevel(property._2)
            Configurator.setLevel(logger, level)
            log.info(s"Set log level: ${logger.getName}=$level")
          } catch {
            case e: Exception => log.error(s"Cannot set log level for: $property", e)
          }
        }
    } catch {
      case e: Exception => log.error("Cannot set log levels", e)
    }
}
