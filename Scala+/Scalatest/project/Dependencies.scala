import sbt._

object Dependencies {
	private val scala = "org.scala-lang" % "scala-library" % "2.11.8" % "provided"
	private val scalatest = "org.scalatest" % "scalatest_2.11" % "3.0.1" % Test
  
	val allDeps = Seq(scala, scalatest)
}
