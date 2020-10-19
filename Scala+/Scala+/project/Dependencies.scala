import sbt._

object Dependencies {
  val allDeps: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % "3.2.2" % Test,
    "org.scalamock" %% "scalamock" % "5.0.0" % Test
  )
}
