package databricks.cluster.longterm

import databricks.Print
import org.apache.spark.SparkContext

object LongTermDatabricksClusterModeApp {
  def main(args: Array[String]): Unit = {
    Print.printInfo(this, args)
    val sc = SparkContext.getOrCreate()
    val durationsSec = args.map(_.toInt).toSeq
    val parallelism = durationsSec.length / 2
    println(s"Parallelism: $parallelism")
    val actualDuration = sc.parallelize(durationsSec, parallelism).map(LongTermLamda.beBusyForSeconds).sum().toInt
    println(s"Actual duration: $actualDuration")
    val expDuration = durationsSec.sum
    println(s"Expected duration: $expDuration")
    assert(actualDuration == expDuration)
    Print.finished()
  }
}
