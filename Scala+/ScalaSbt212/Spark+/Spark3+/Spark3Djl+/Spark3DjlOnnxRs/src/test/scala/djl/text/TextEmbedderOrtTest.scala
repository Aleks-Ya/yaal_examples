package djl.text

import ai.djl.onnxruntime.engine.OrtEngine
import ai.djl.spark.task.text.TextEmbedder
import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.StructType
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TextEmbedderOrtTest extends AnyFlatSpec with Matchers {

  it should "generate embeddings by OnnxRuntime engine from Zoo" in {
    val schema = StructType.fromDDL("id INTEGER, text STRING")
    val df = Factory.createDf(schema,
      Row(1, "Hello world"),
      Row(2, "Apache Spark is great"))
    val embedder = new TextEmbedder()
      .setInputCol("text")
      .setOutputCol("embedding")
      .setEngine(OrtEngine.ENGINE_NAME)
    //      .setModelUrl("djl://ai.djl.huggingface.pytorch/sentence-transformers/all-MiniLM-L6-v2")
    val outputDf = embedder.embed(df)
    outputDf.show()
    //    df.toJSON.collect() should contain inOrderOnly(
    //      """{"text":"Hello world","words":["hello","world"]}""",
    //      """{"text":"Apache Spark is great","words":["apache","spark","is","great"]}"""
    //    )
  }

  it should "generate embeddings by all-mpnet-base-v2 from Zoo" in {
    val schema = StructType.fromDDL("id INTEGER, text STRING")
    val df = Factory.createDf(schema,
      Row(1, "hello"),
      Row(2, "Apache Spark is great"))
    val embedder = new TextEmbedder()
      .setInputCol("text")
      .setOutputCol("embedding")
      .setEngine(OrtEngine.ENGINE_NAME)
      .setModelUrl("djl://ai.djl.huggingface.onnxruntime/sentence-transformers/all-mpnet-base-v2")
    val outputDf = embedder.embed(df)
    outputDf.show()
  }

  it should "generate embeddings by all-mpnet-base-v2 from zip" in {
    val schema = StructType.fromDDL("id INTEGER, text STRING")
    val df = Factory.createDf(schema,
      Row(1, "hello"),
      Row(2, "Apache Spark is great"))
    val embedder = new TextEmbedder()
      .setInputCol("text")
      .setOutputCol("embedding")
      .setEngine(OrtEngine.ENGINE_NAME)
      .setModelUrl("/home/aleks/models/OpenSearch/all-mpnet-base-v2-1.0.1-onnx/sentence-transformers_all-mpnet-base-v2-1.0.1-onnx/all-mpnet-base-v2.onnx")
    val outputDf = embedder.embed(df)
    outputDf.show()
  }
}
