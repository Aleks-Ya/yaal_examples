package spark3.ml.tokenizer

import org.apache.spark.ml.feature.Tokenizer
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StructType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark3.ml.Factory

class TokenizerTest extends AnyFlatSpec with Matchers {
  it should "tokenize text" in {
    val schema = StructType.fromDDL("text STRING")
    val textDf = Factory.createDf(schema,
      Row("Hello world"),
      Row("Apache Spark is great"))
    val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    val tokenizedDf = tokenizer.transform(textDf)
    tokenizedDf shouldContain(
      """{"text":"Hello world","words":["hello","world"]}""",
      """{"text":"Apache Spark is great","words":["apache","spark","is","great"]}"""
    )
  }
}
