import sbt._

object Dependencies {
  val allDeps = Seq(
    "org.apache.spark" % "spark-sql_2.11" % "2.1.0",
    "com.databricks" % "spark-csv_2.11" % "1.5.0",
    "org.scalatest" % "scalatest_2.11" % "3.0.1" % Test)
}
