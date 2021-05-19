import Dependencies.{scalaTestDep, spark3SqlDep, h2Dep}

lazy val Spark3Sql = (project in file("."))
  .settings(libraryDependencies ++= Seq(spark3SqlDep, scalaTestDep, h2Dep))
