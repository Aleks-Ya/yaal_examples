import sbt._

object Dependencies {
  val allDeps: Seq[ModuleID] = Seq(
    "org.scalatest" % "scalatest_2.12" % "3.0.5" % Test,
    "org.scalamock" %% "scalamock" % "4.1.0" % Test
  )
}
