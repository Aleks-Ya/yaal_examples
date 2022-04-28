import Dependencies._

lazy val SprayJson = (project in file("."))
  .settings(libraryDependencies ++= Seq(sprayJsonDep, scalaTestDep))
