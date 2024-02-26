package databricks.parameters

import databricks.{Print, WordCount}

object JobParamsApp {
  def main(args: Array[String]): Unit = {
    Print.printInfo(this, args)
    WordCount.countWords(args)
    Print.finished()
  }
}
