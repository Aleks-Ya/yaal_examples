import sbt.*

object Dependencies {
  val allDeps: Seq[ModuleID] = Seq(
    "org.apache.spark" %% "spark-sql" % "3.5.7" % "provided",
    "com.vladsch.flexmark" % "flexmark-all" % "0.64.8" % "provided" //Provided by "spark-submit --packages"
  )
}
