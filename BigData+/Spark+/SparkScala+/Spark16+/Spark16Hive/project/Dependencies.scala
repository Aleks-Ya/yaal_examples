import sbt._

object Dependencies {

  val compileDeps = Seq()

  val providedDeps = Seq(
    "org.apache.spark" % "spark-hive_2.11" % "1.6.2"
  )

  val testDeps = Seq("org.scalatest" % "scalatest_2.11" % "3.0.1" % Test)
}
