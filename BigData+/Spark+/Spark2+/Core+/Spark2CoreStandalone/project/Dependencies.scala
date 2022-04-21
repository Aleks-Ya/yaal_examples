import sbt._

object Dependencies {
  val allDeps: Seq[ModuleID] = Seq(
    "org.apache.spark" %% "spark-sql" % "3.2.1"
  )
}
