package engrus

import org.apache.spark.ml.feature.{HashingTF, IDF, IDFModel, Tokenizer}
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.scalatest.{FlatSpec, Matchers}

/**
  * ML determines language of text: English or Russian.
  */
class EnglishRussianTest extends FlatSpec with Matchers {

  private val wordsColumn = "words"
  private val featuresColumn = "features"
  private val rawFeaturesColumn = "rawFeatures"
  private val sentenceColumn = "sentence"
  private val labelColumn = "label"

  it should "a" in {
    val ss = SparkSession.builder
      .appName(classOf[EnglishRussianTest].getSimpleName)
      .master("local[2]")
      .getOrCreate

    val rusLabel = 1d
    val engLabel = 0d

    val rusLabelledWords = parseWords(ss, readResource(ss, "rus_book.txt"), rusLabel)
    val engLabelledWords = parseWords(ss, readResource(ss, "eng_book.txt"), engLabel)
    val allLabelledWords = rusLabelledWords.union(engLabelledWords)
    allLabelledWords.show

    val hashingTF = new HashingTF().setInputCol(wordsColumn).setOutputCol(featuresColumn).setNumFeatures(20)
    val featurizedData = hashingTF.transform(allLabelledWords)
    featurizedData.show

    val estimator = new LinearRegression().setMaxIter(10)
    val model = estimator.fit(featurizedData)

    //    val rescaledData = idfModel.transform(featurizedData)
    //    rescaledData.show()

    val sqlContext = ss.sqlContext
    import sqlContext.implicits._
    val lines = "Мороз и солнце день чудесный" :: "Еще ты дремлешь друг прелестный" :: Nil
    val rusLabelledWordsTest = parseWords(ss, ss.createDataset(lines), rusLabel)
    //    val rusLines = ss.createDataset(lines).map(line => line.split(" ")).toDF(wordsColumn)
    rusLabelledWordsTest.createOrReplaceTempView("test")
    val featurizedData2 = hashingTF.transform(rusLabelledWordsTest)
    featurizedData2.show
    val prediction = model.transform(featurizedData2)
    prediction.show

    //      prediction.select(featuresColumn, "label", "myProbability", "prediction")
    //      .collect()
    //      .foreach { case Row(features: Vector[Double], label: Double, prob: Vector[Double], prediction: Double) =>
    //        println(s"($features, $label) -> prob=$prob, prediction=$prediction")
    //      }

    ss.stop()
  }

  private def readResource(ss: SparkSession, resource: String) = {
    ss.read.textFile(this.getClass.getResource(resource).getFile)
  }

  private def parseWords(ss: SparkSession, text: Dataset[String], label: Double) = {
    val sqlContext = ss.sqlContext
    import sqlContext.implicits._

    val labelledSentences = text.map(line => (label, line)).toDF(labelColumn, sentenceColumn)

    val tokenizer = new Tokenizer().setInputCol(sentenceColumn).setOutputCol(wordsColumn)
    tokenizer.transform(labelledSentences)
  }

}
