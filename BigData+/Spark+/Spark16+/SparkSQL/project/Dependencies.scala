import sbt._

object Dependencies {
	private val scala = "org.scala-lang" % "scala-library" % "2.11.8" % "provided"
	private val sparkCore = "org.apache.spark" % "spark-core_2.11" % "1.6.2" % "provided"
	private val sparkSql = "org.apache.spark" % "spark-sql_2.11" % "1.6.2" % "provided"
	private val sparkHive = "org.apache.spark" % "spark-hive_2.11" % "1.6.2" % "provided"
	private val scalatest = "org.scalatest" % "scalatest_2.11" % "3.0.1" % Test
  
	val allDeps = Seq(scala, sparkCore, sparkSql, sparkHive, scalatest)
}
