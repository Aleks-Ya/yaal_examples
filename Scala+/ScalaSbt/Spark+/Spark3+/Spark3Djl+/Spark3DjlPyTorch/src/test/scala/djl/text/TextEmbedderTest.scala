package djl.text

import ai.djl.spark.task.text.TextEmbedder
import factory.Factory
import org.apache.spark.ml.feature.Tokenizer
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StructType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TextEmbedderTest extends AnyFlatSpec with Matchers {
  it should "tokenize text" in {
    val schema = StructType.fromDDL("id INTEGER, text STRING")
    val df = Factory.createDf(schema,
      Row(1, "Hello world"),
      Row(2, "Apache Spark is great"))
    val embedder = new TextEmbedder()
      .setInputCol("text")
      .setOutputCol("embedding")
      .setEngine("PyTorch")
      .setModelUrl("djl://ai.djl.huggingface.pytorch/sentence-transformers/all-MiniLM-L6-v2")
    val outputDf = embedder.embed(df)
    outputDf.show()
//    df.toJSON.collect() should contain inOrderOnly(
//      """{"text":"Hello world","words":["hello","world"]}""",
//      """{"text":"Apache Spark is great","words":["apache","spark","is","great"]}"""
//    )
  }
}
