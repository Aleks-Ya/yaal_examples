import sbt._

object Dependencies {
  val allDeps = Seq(
    "org.scala-lang" % "scala-library" % "2.11.8" % "provided",
    "org.apache.spark" % "spark-core_2.11" % "1.6.2" % "provided",
    "org.apache.spark" % "spark-streaming_2.11" % "1.6.2" % "provided",
    "org.apache.spark" % "spark-sql_2.11" % "1.6.2" % "provided",
    "org.apache.spark" % "spark-hive_2.11" % "1.6.2" % "provided",
    "org.scalatest" % "scalatest_2.11" % "3.0.1" % Test)
}
