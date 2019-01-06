import sbt._

object Dependencies {
  val allDeps: Seq[ModuleID] = Seq(
    "org.apache.spark" % "spark-core_2.11" % "2.2.1",
    "org.scalatest" % "scalatest_2.11" % "3.0.5" % Test)
}
