import sbt._

object Dependencies {
  val allDeps = Seq(
    "org.apache.spark" % "spark-streaming_2.11" % "2.1.0",
    "org.scalatest" % "scalatest_2.11" % "3.0.1" % Test)
}
