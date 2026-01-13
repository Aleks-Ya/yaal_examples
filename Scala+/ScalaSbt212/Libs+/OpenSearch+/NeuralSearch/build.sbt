import Dependencies.{openSearchDep, scalaTestDep}

lazy val NeuralSearch = (project in file("."))
  .settings(libraryDependencies ++= Seq(scalaTestDep, openSearchDep))