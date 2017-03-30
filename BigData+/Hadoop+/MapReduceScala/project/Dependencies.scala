import sbt._

object Dependencies {
  private val hadoopVersion = "2.8.0"
  val allDeps = Seq(
    "org.apache.hadoop" % "hadoop-client" % s"$hadoopVersion",
    "org.apache.hadoop" % "hadoop-common" % s"$hadoopVersion",
//    "org.apache.mrunit" % "mrunit" % "1.1.0",
    "org.scalatest" % "scalatest_2.11" % "3.0.1" % Test,
    "org.apache.hadoop" % "hadoop-minicluster" % s"$hadoopVersion" % Test
  )
}