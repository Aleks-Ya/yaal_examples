package metadata

import org.apache.spark.{SparkConf, SparkContext}

object JobMetadataApp {

  def main(args: Array[String]): Unit = {
    println("Start")
    val conf = new SparkConf().setAppName(getClass.getSimpleName)
    val sc = new SparkContext(conf)

    val jobGroupId1 = "Sum of numbers"
    sc.setJobGroup(jobGroupId1, "Sum of integers")
    sc.addJobTag("tag1")
    println(sc.parallelize(Seq(1, 2, 3)).sum)

    sc.setJobDescription("Sum of doubles")
    sc.addJobTag("tag2")
    println(sc.parallelize(Seq(1.1, 2.1, 3.1)).sum)

    sc.clearJobGroup()
    val jobGroupId2 = "Count elements"
    sc.setJobGroup(jobGroupId2, "Count integers")
    sc.addJobTag("tag3")
    sc.addJobTag("tag4")
    println(sc.parallelize(Seq(1, 2, 3)).count)

    sc.setJobDescription("Count doubles")
    println(sc.parallelize(Seq(1.1, 2.1, 3.1)).count)

    sc.stop()
    println("Finish")
  }

}
