package databricks.cluster.shorttrem

import databricks.{Print, WordCount}

object ShortTermDatabricksClusterModeApp {
  def main(args: Array[String]): Unit = {
    Print.printInfo(this, args)
    WordCount.countWords(args)
    Print.finished()
  }
}
