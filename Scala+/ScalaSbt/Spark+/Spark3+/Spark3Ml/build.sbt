import Dependencies.{scalaTestDep, spark3MlDep}

lazy val Spark3Ml = (project in file("."))
  .dependsOn(Projects.UtilSrc)
  .settings(libraryDependencies ++= Seq(scalaTestDep, spark3MlDep))
