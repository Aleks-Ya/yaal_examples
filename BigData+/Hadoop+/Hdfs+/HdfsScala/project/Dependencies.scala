import sbt._

object Dependencies {
  private val hadoopVersion = "2.7.3"
  val compileDeps = Seq(
    "org.apache.hadoop" % "hadoop-hdfs" % s"$hadoopVersion",
    "org.apache.hadoop" % "hadoop-common" % s"$hadoopVersion"
  )

  val providedDeps: Seq[ModuleID] = Seq()

  val testDeps = Seq("org.scalatest" % "scalatest_2.11" % "3.0.1" % Test)
}