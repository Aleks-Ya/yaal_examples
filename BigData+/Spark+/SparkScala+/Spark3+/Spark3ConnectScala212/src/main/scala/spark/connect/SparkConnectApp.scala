package spark.connect

import org.apache.spark.sql.SparkSession

object SparkConnectApp {
  def main(args: Array[String]): Unit = {
    val ss = SparkSession.builder.remote("sc://spark-standalone-cluster-master:15002").getOrCreate()
    import ss.implicits._
    val data = Seq(("John", 25), ("Peter", 35))
    val df = data.toDF("name", "age")
    df.show()
  }
}
