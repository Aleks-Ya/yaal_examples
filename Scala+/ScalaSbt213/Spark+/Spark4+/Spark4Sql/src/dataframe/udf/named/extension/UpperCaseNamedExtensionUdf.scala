package dataframe.udf.named.extension

import dataframe.udf.named.extension.UdfExtensions.NamedUdf
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
