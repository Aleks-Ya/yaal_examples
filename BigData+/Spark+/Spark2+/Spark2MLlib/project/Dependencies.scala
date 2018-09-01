import sbt._

object Dependencies {
  val allDeps = Seq(
    "org.apache.spark" % "spark-mllib_2.11" % "2.3.1" exclude("org.scalatest", "scalatest_2.11"),
    "org.scalatest" % "scalatest_2.11" % "3.0.5" % Test)
}
