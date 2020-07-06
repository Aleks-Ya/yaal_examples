import sbt._

object Dependencies {
  val allDeps = Seq(
    "com.github.scopt" % "scopt_2.12" % "3.7.1",
    "org.scalatest" % "scalatest_2.12" % "3.0.5" % Test
  )
}
