package ml.tokenizer

import factory.Factory
import org.apache.spark.ml.feature.RegexTokenizer
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StructType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RegexTokenizerTest extends AnyFlatSpec with Matchers {
  it should "tokenize text" in {
    val schema = StructType.fromDDL("text STRING")
    val textDf = Factory.createDf(schema,
      Row("Hello_world"),
      Row("Apache Spark_is_great"))
    val tokenizer = new RegexTokenizer().setInputCol("text").setOutputCol("words").setPattern("_+")
    val tokenizedDf = tokenizer.transform(textDf)
    tokenizedDf.toJSON.collect() should contain inOrderOnly(
      """{"text":"Hello_world","words":["hello","world"]}""",
      """{"text":"Apache Spark_is_great","words":["apache spark","is","great"]}"""
    )
  }
}
