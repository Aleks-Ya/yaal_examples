package spark.streaming.dstream.mkuthan_unit_testing

import org.apache.spark.SparkContext
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Duration, StreamingContext, Time}

case class WordCount(word: String, count: Int)

object WordCount {

  type WordHandler = (RDD[WordCount], Time) => Unit

  def count(sc: SparkContext, lines: RDD[String]): RDD[WordCount] = count(sc, lines, Set())

  def count(sc: SparkContext, lines: RDD[String], stopWords: Set[String]): RDD[WordCount] = {
    val stopWordsVar = sc.broadcast(stopWords)
    val words = prepareWords(lines, stopWordsVar)
    val wordCounts = words
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .map { case (word: String, count: Int) => WordCount(word, count) }
    val sortedWordCounts = wordCounts.sortBy(_.word)
    sortedWordCounts
  }

  def count(ssc: StreamingContext,
            lines: DStream[String],
            windowDuration: Duration,
            slideDuration: Duration)
           (handler: WordHandler): Unit = count(ssc, lines, windowDuration, slideDuration, Set())(handler)

  def count(ssc: StreamingContext,
            lines: DStream[String],
            windowDuration: Duration,
            slideDuration: Duration,
            stopWords: Set[String])
           (handler: WordHandler): Unit = {
    val sc = ssc.sparkContext
    val stopWordsVar = sc.broadcast(stopWords)
    val words = lines.transform(prepareWords(_, stopWordsVar))
    val wordCounts = words.map(x => (x, 1))
      .reduceByKeyAndWindow(_ + _, _ - _, windowDuration, slideDuration)
      .map { case (word: String, count: Int) => WordCount(word, count) }
    wordCounts.foreachRDD((rdd: RDD[WordCount], time: Time) => {
      handler(rdd.sortBy(_.word), time)
    })
  }

  private def prepareWords(lines: RDD[String], stopWords: Broadcast[Set[String]]): RDD[String] = {
    lines.flatMap(_.split("\\s"))
      .map(_.stripSuffix(",").stripPrefix(".").toLowerCase)
      .filter(!stopWords.value.contains(_))
      .filter(_.nonEmpty)
  }

}
