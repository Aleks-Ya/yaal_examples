import Dependencies.{h2Dep, scalaTestDep, spark3SqlDep, sparkSqlKafkaDep, embeddedKafkaDep}

lazy val Spark3Sql = (project in file("."))
  .dependsOn(Projects.UtilSrc)
  .settings(libraryDependencies ++= Seq(spark3SqlDep, scalaTestDep, h2Dep, sparkSqlKafkaDep, embeddedKafkaDep))
