package ml.tokenizer

import factory.Factory
import org.apache.spark.ml.feature.Tokenizer
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StructType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TokenizerTest extends AnyFlatSpec with Matchers {
  it should "tokenize text" in {
    val schema = StructType.fromDDL("text STRING")
    val textDf = Factory.createDf(schema,
      Row("Hello world"),
      Row("Apache Spark is great"))
    val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    val tokenizedDf = tokenizer.transform(textDf)
    tokenizedDf.toJSON.collect() should contain inOrderOnly(
      """{"text":"Hello world","words":["hello","world"]}""",
      """{"text":"Apache Spark is great","words":["apache","spark","is","great"]}"""
    )
  }
}
