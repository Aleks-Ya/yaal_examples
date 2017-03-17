package engrus

import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.sql.SparkSession
import org.scalatest.{FlatSpec, Matchers}

/**
  * ML determines language of text: English or Russian.
  */
class EnglishRussianTest extends FlatSpec with Matchers {

  it should "a" in {
    val ss = SparkSession.builder().appName("English or Russian").master("local[2]").getOrCreate()
    val sqlContext = ss.sqlContext
    import sqlContext.implicits._

    val engStringDs = ss.read.textFile(this.getClass.getResource("eng_book.txt").getFile)
    val rusStringDs = ss.read.textFile(this.getClass.getResource("rus_book.txt").getFile)

    val rusLabel = 1d
    val engLabel = 0d

    val engWordDf = engStringDs.map(line => (engLabel, line)).toDF("label", "sentence")
    val rusWordDf = rusStringDs.map(line => (rusLabel, line)).toDF("label", "sentence")
    val sentenceData = engWordDf.union(rusWordDf)
    sentenceData.show

    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val wordsData = tokenizer.transform(sentenceData)
    wordsData.show

    val hashingTF = new HashingTF()
      .setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(20)


    val featurizedData = hashingTF.transform(wordsData)
    featurizedData.show
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)

    val rescaledData = idfModel.transform(featurizedData)
    rescaledData.select("label", "features").show()

    val data = "Мороз и солнце день чудесный" :: "Еще ты дремлешь друг прелестный" :: Nil
    val rusTest = ss.createDataset(data)
      .map(line => line.split(" ")).toDF("words")
    val featurizedData2 = hashingTF.transform(rusTest)

    idfModel.transform(featurizedData2).show

    ss.stop()
  }
}
