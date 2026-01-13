import Dependencies.{embeddedKafkaDep, scalaTestDep, spark3SqlDep, sparkSqlKafkaDep}

lazy val Spark3SqlKafka = (project in file("."))
  .dependsOn(Projects.UtilSrc)
  .settings(libraryDependencies ++= Seq(spark3SqlDep, scalaTestDep, sparkSqlKafkaDep, embeddedKafkaDep))
