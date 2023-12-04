import sbt.*

object Dependencies {
  val allDeps: Seq[ModuleID] = Seq(
    "org.apache.spark" %% "spark-sql" % "3.5.0"
  )
}
