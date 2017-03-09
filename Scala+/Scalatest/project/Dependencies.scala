import sbt._

object Dependencies {
  val allDeps = Seq(
    "org.scala-lang" % "scala-library" % "2.11.8" % Provided,
    "org.scalatest" % "scalatest_2.11" % "3.0.1" % Test)
}
