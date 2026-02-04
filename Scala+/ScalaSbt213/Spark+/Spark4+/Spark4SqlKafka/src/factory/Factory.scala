package factory

import org.apache.spark.sql._

object Factory {

  lazy val ss: SparkSession = {
    val builder = SparkSession.builder()
      .config("spark.sql.jsonGenerator.ignoreNullFields", "false")
      .appName(getClass.getSimpleName)
      .master("local")

    val logDir = sys.env.get("SPARK_HISTORY_FS_LOG_DIRECTORY")
    if (logDir.isDefined) {
      builder
        .config("spark.eventLog.enabled", "true")
        .config("spark.eventLog.dir", logDir.get)
    }

    builder.getOrCreate()
  }

}
