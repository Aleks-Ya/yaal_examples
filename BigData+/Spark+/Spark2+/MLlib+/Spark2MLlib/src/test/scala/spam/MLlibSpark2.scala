package spam

import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.HashingTF
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.sql.SparkSession

/**
  * Пример из книги "Изучаем Spark - молниеносный анализ данных".
  * Переписал на Spark 2 API.
  */
object MLlibSpark2 {

  def main(args: Array[String]) {
//    val ss = SparkSession.builder().appName(s"Book example: Scala").master("local[2]").getOrCreate()
//    val sql = ss.sqlContext
//    import sql.implicits._
//
//    val wordsColumn = "words"
//    val featuresColumn = "features"
//    val rawFeaturesColumn = "rawFeatures"
//    val sentenceColumn = "sentence"
//    val labelColumn = "label"
//
//
//    // Load 2 types of emails from text files: spam and ham (non-spam).
//    // Each line has text from one email.
//    val spam = sql.read.textFile(this.getClass.getResource("spam.txt").getFile).toDF("sentences")
//    val ham = sql.read.textFile(this.getClass.getResource("ham.txt").getFile).toDF("sentences")
//
//    // Create a HashingTF instance to map email text to vectors of 100 features.
//    // Each email is split into words, and each word is mapped to one feature.
//    val spamWords = spam.map(email => email.getString(0).split(" ")).toDF(wordsColumn)
//    //    val spamFeatures = spam.map(email => email.getString(0).split(" ")).toDF("words")
//    //      .map(words => tf.transform(words))
//    //    val hamFeatures = ham.map(email => tf.transform(email.getString(0).split(" ")))
//    val hamWords = ham.map(email => email.getString(0).split(" ")).toDF(wordsColumn)
//
//    val tf = new HashingTF().setInputCol(wordsColumn).setOutputCol(rawFeaturesColumn).setNumFeatures(20)
//    val spamFeatures = tf.transform(spamWords)
//    val hamFeatures = tf.transform(hamWords)
//
//    // Create LabeledPoint datasets for positive (spam) and negative (ham) examples.
//    val positiveExamples = spamFeatures.map(row => LabeledPoint(1, row.getAs(0)))
//    val negativeExamples = hamFeatures.map(row => LabeledPoint(0, row.getAs(0)))
//    val trainingData = positiveExamples.union(negativeExamples)
//    trainingData.cache() // Cache data since Logistic Regression is an iterative algorithm.
//
//    // Create a Logistic Regression learner which uses the LBFGS optimizer.
//    val lrLearner = new LogisticRegression()
//    // Run the actual learning algorithm on the training data.
//    val model = lrLearner.fit(trainingData)
//
//    // Test on a positive example (spam) and a negative one (ham).
//    // First apply the same HashingTF feature transformation used on the training data.
//    val posTestVector = tf.transform("O M G GET cheap stuff by sending money to ...".split(" "))
//    val negTestVector = tf.transform("Hi Dad, I started studying Spark the other ...".split(" "))
//
//    val test = ss.createDataFrame(Seq(
//      (1.0, posTestVector),
//      (0.0, negTestVector))
//    ).toDF("label", "features")
//
//
//    model.transform(test).show
//    // Now use the learned model to predict spam/ham for new emails.
//    //    println(s"Prediction for positive test example: ${model.predict(posTestExample)}")
//    //    println(s"Prediction for negative test example: ${model.predict(negTestExample)}")
//
//    ss.stop()
  }
}
