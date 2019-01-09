import sbt._

object Dependencies {
  val allDeps = Seq(
    "org.apache.spark" % "spark-sql_2.11" % "2.4.0" exclude("org.scalatest", "scalatest_2.11"),
    "org.scalatest" % "scalatest_2.11" % "3.0.5" % Test,
    "com.h2database" % "h2" % "1.4.197" % Test
  )
}
