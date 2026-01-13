import Dependencies.{awaitilityScalaDep, scalaTestDep}

lazy val AwaitilityScala = (project in file("."))
  .dependsOn(Projects.UtilSrc)
  .settings(libraryDependencies ++= Seq(awaitilityScalaDep, scalaTestDep))
