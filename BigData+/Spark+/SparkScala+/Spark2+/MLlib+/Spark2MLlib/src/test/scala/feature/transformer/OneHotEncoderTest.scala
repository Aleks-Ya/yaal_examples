package feature.transformer

import factory.Factory
import org.apache.spark.ml.feature.{OneHotEncoder, StringIndexer}
import org.scalatest.{FlatSpec, Matchers}

class OneHotEncoderTest extends FlatSpec with Matchers {

  /**
    * Source: http://spark.apache.org/docs/latest/ml-features.html#onehotencoder
    */
  it should "works with DataFrame" in {
    val df = Factory.ss.sqlContext.createDataFrame(Seq(
      (0, "a"),
      (1, "b"),
      (2, "c"),
      (3, "a"),
      (4, "a"),
      (5, "c")
    )).toDF("id", "category")

    val indexer = new StringIndexer()
      .setInputCol("category")
      .setOutputCol("categoryIndex")
      .fit(df)
    val indexed = indexer.transform(df)

    val encoder = new OneHotEncoder()
      .setInputCol("categoryIndex")
      .setOutputCol("categoryVec")

    val encoded = encoder.transform(indexed)
    encoded.show()
  }

  it should "works with Dataset" in {
//    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[CategoryIndex]
//    val indexed = Factory.ss.createDataset(Seq(
//      new CategoryIndex(1, null),
//      new CategoryIndex(2, null)
//    )).map(obj => (obj.categoryIndex, obj.categoryVector)).toDF()
//
//    indexed.show
//    indexed.toDF.show
//
//    val encoder = new OneHotEncoder()
//      .setInputCol("categoryIndex")
//      .setOutputCol("categoryVec")
//
//
//    val encoded = encoder.transform(indexed)
//    encoded.show()
  }

}

class CategoryIndex(var categoryIndex: Int, var categoryVector: org.apache.spark.ml.linalg.Vector) {}
