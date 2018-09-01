import sbt._

object Dependencies {

  val compileDeps = Seq()

  val providedDeps = Seq(
    "org.apache.spark" % "spark-hive_2.11" % "2.3.1"
  )

  val testDeps = Seq("org.scalatest" % "scalatest_2.11" % "3.0.5" % Test)
}
