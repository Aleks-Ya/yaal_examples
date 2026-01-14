package djl.text

import ai.djl.huggingface.translator.TextEmbeddingTranslatorFactory
import ai.djl.spark.ModelLoader
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ModelLoaderTest extends AnyFlatSpec with Matchers {

  // NOT FINISHED
  it should "load model" in {
    val translatorFactory = new TextEmbeddingTranslatorFactory()
    val arguments = new java.util.HashMap[String, AnyRef]()
    val modelLoader = new ModelLoader("pytorch", "djl://ai.djl.huggingface.pytorch/sentence-transformers/all-MiniLM-L6-v2",
      classOf[String], classOf[Seq[Float]],
      translatorFactory, arguments)
    val predictor = modelLoader.newPredictor()
  }
}
