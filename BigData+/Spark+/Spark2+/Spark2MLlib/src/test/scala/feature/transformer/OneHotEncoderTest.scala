package feature.transformer

import org.apache.spark.ml.feature.{OneHotEncoder, StringIndexer}
import org.apache.spark.sql.SparkSession
import org.scalatest.{FlatSpec, Matchers}

class OneHotEncoderTest extends FlatSpec with Matchers {

  it should "works" in {
    val ss = SparkSession.builder
      .appName(classOf[OneHotEncoderTest].getSimpleName)
      .master("local[2]")
      .getOrCreate

    val df = ss.sqlContext.createDataFrame(Seq(
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

}
