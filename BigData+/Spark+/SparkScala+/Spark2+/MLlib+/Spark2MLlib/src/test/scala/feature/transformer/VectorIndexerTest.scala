package feature.transformer

import factory.Factory
import org.apache.spark.ml.feature.VectorIndexer
import org.scalatest.{FlatSpec, Matchers}

/**
  * Source: http://spark.apache.org/docs/latest/ml-features.html#vectorindexer
  */
class VectorIndexerTest extends FlatSpec with Matchers {

  it should "works" in {
    val resource = getClass.getResource("sample_libsvm_data.txt")
    val data = Factory.ss.read.format("libsvm").load(resource.toString)

    val indexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexed")
      .setMaxCategories(10)

    val indexerModel = indexer.fit(data)

    val categoricalFeatures: Set[Int] = indexerModel.categoryMaps.keys.toSet
    println(s"Chose ${categoricalFeatures.size} categorical features: " +
      categoricalFeatures.mkString(", "))

    // Create new column "indexed" with categorical values transformed to indices
    val indexedData = indexerModel.transform(data)
    indexedData.show()
  }

}
