package dataframe.udf.named.`trait`

import org.apache.spark.sql.Column
import org.apache.spark.sql.functions.udf

private object UpperCaseNamedTraitUdf extends Serializable with NamedUdf {
  def apply(name: Column): Column = udf((name: String) => name.toUpperCase)
    .withName(udfName)
    .apply(name)
}
