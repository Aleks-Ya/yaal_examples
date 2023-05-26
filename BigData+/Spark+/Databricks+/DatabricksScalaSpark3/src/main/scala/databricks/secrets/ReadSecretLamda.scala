package databricks.secrets

import com.databricks.dbutils_v1.DBUtilsHolder.dbutils

import scala.util.Try

object ReadSecretLamda {
  def stringToLength(str: String): Long = {
    val e = Try(dbutils.secrets).failed.get
    println(s"Exception: $e")
    assert(e.isInstanceOf[NullPointerException])
    str.length
  }
}
