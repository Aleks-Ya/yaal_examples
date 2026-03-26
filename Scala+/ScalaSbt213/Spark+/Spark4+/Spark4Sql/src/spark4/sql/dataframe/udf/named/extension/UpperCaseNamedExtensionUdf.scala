package spark4.sql.dataframe.udf.named.extension

import UdfExtensions.NamedUdf
import org.apache.spark.sql.Column
import org.apache.spark.sql.functions.udf

private object UpperCaseNamedExtensionUdf extends Serializable {
  def apply(name: Column): Column = udf((name: String) => name.toUpperCase)
    .named
    .apply(name)
}

private object UpperCaseNamedExtensionCustomNameUdf extends Serializable {
  def apply(name: Column): Column = udf((name: String) => name.toUpperCase)
    .named("MyUpperCaseUdf")
    .apply(name)
}
