import Dependencies.{sprayJsonDep, scalaTestDep}

lazy val SprayJson = (project in file("."))
  .settings(libraryDependencies ++= Seq(sprayJsonDep, scalaTestDep))
