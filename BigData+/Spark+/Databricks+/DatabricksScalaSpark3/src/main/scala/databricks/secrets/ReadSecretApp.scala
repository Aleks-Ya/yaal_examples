package databricks.secrets

import com.databricks.dbutils_v1.DBUtilsHolder.dbutils
import databricks.{Print, WordCount}

object ReadSecretApp {
  def main(args: Array[String]): Unit = {
    Print.printInfo(this, args)
    val scope = "iablokov-tmp"
    val key = "db-pass"
    val value = dbutils.secrets.get(scope = scope, key = key)
    println(s"Secret in driver: scope=$scope, key=$key, value=${value.mkString(" ")}")
    WordCount.countWords(args)
    Print.finished()
  }
}
