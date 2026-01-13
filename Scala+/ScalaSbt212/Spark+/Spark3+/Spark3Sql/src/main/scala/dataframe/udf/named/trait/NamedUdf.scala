package dataframe.udf.named.`trait`

private trait NamedUdf {
  protected def udfName: String = getClass.getSimpleName.replaceAll("\\$", "")
}
