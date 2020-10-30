import Dependencies.scalaTestPlusPlayDep

lazy val scalaTestPlusPlay = (project in file(".")).
  settings(
    name := "scala-test-plus-play",
    connectInput in run := true,
    libraryDependencies ++= Seq(guice, scalaTestPlusPlayDep)
  ).enablePlugins(PlayScala)
