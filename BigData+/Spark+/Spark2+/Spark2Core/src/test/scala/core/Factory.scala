package core

import org.apache.spark.{SparkConf, SparkContext}

object Factory {

  lazy val sc: SparkContext = {
    val conf = new SparkConf()
      .setAppName(getClass.getSimpleName)
      .setMaster("local")
    setEventLogDir(conf)
    new SparkContext(conf)
  }

  private def setEventLogDir(conf: SparkConf) = {
    val logDir = sys.env.get("SPARK_HISTORY_FS_LOG_DIRECTORY")
    if (logDir.isDefined) {
      conf
        .set("spark.eventLog.enabled", "true")
        .set("spark.eventLog.dir", logDir.get)
      println("Set event log dir: " + logDir.get)
    }
  }
}