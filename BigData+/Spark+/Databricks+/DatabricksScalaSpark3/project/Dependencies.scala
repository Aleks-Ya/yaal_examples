import sbt.*

object Dependencies {
  val scalaVer = "2.12.20"
  val allDeps: Seq[ModuleID] = Seq(
    "org.scala-lang" % "scala-library" % scalaVer,
    "org.apache.spark" %% "spark-sql" % "3.5.5",
    "com.databricks" %% "dbutils-api" % "0.0.6" // For secrets (com.databricks.dbutils_v1.DBUtilsHolder.dbutils)
  )
}
