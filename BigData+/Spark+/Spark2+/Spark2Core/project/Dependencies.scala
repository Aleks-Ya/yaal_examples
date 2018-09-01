import sbt._

object Dependencies {
  val allDeps = Seq(
    "org.apache.spark" % "spark-core_2.11" % "2.3.1",
    "org.scalatest" % "scalatest_2.11" % "3.0.5" % Test)
}
