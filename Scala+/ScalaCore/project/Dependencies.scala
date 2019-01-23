import sbt._

object Dependencies {
  val allDeps: Seq[ModuleID] = Seq(
    "org.scalatest" % "scalatest_2.12" % "3.0.5" % Test
  )
}
