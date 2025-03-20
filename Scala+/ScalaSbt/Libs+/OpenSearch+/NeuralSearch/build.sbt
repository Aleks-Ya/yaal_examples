import Dependencies.{opensearchDep, scalaTestDep}

lazy val NeuralSearch = (project in file("."))
  .settings(libraryDependencies ++= Seq(scalaTestDep, opensearchDep))