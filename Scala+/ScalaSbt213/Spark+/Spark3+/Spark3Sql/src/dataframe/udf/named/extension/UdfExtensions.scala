package dataframe.udf.named.extension

import org.apache.spark.sql.expressions.UserDefinedFunction

private object UdfExtensions {
  implicit class NamedUdf(val udf: UserDefinedFunction) extends AnyVal {
    def named: UserDefinedFunction = {
      val trace = Thread.currentThread.getStackTrace
      if (trace.length >= 2) {
        udf.withName(trace(2).getClassName.split('.').last.replaceAll("\\$", ""))
      } else {
        udf
      }
    }

    def named(udfName: String): UserDefinedFunction = udf.withName(udfName)
  }
}
