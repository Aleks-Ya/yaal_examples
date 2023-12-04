package engrus

import factory.Factory
import org.apache.spark.ml.feature._
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.{Dataset, SparkSession}
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
    val ss = Factory.ss

    val rusLabel = 1d
    val engLabel = 0d

    val rusLabelledWords = parseWords(ss, readResource(ss, "rus_book.txt"), rusLabel)
    rusLabelledWords.show
    val engLabelledWords = parseWords(ss, readResource(ss, "eng_book.txt"), engLabel)
    engLabelledWords.show
    val allLabelledWords = rusLabelledWords.union(engLabelledWords)
    allLabelledWords.show

    val hashingTF = new HashingTF().setInputCol(wordsColumn).setOutputCol(featuresColumn).setNumFeatures(1000)
    val featurizedData = hashingTF.transform(allLabelledWords)
    featurizedData.show

    val estimator = new LinearRegression().setMaxIter(10)
    val model = estimator.fit(featurizedData)

    val sqlContext = ss.sqlContext
    import sqlContext.implicits._
    //    val lines = "Мороз и солнце день чудесный" :: "Еще ты дремлешь друг прелестный" :: Nil
    val lines = "Мне как обычно и" :: "the gun roared a second time" :: Nil
    val rusLabelledWordsTest = parseWords(ss, ss.createDataset(lines), -1)
    rusLabelledWordsTest.createOrReplaceTempView("test")
    val featurizedData2 = hashingTF.transform(rusLabelledWordsTest)
    featurizedData2.show
    val prediction = model.transform(featurizedData2)
    prediction.show

    ss.stop()
  }

  private def readResource(ss: SparkSession, resource: String) = {
    ss.read.textFile(this.getClass.getResource(resource).getFile)
  }

  private def parseWords(ss: SparkSession, text: Dataset[String], label: Double) = {
    val sqlContext = ss.sqlContext
    import sqlContext.implicits._

    val labelledSentences = text.filter(_.nonEmpty).map(line => (label, line)).toDF(labelColumn, sentenceColumn)

    val tokenizer = new Tokenizer().setInputCol(sentenceColumn).setOutputCol("with_stop_words")
    val df = tokenizer.transform(labelledSentences)

    val remover = new StopWordsRemover()
      .setInputCol("with_stop_words")
      .setOutputCol(wordsColumn)
      .setStopWords(Array("", " ", "—", "-"))
    remover.transform(df)
  }

}
