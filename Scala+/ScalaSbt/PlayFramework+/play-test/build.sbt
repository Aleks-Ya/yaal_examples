import Dependencies.playTestDep

lazy val playTest = (project in file(".")).
  settings(
    name := "play-test",
    connectInput in run := true,
    libraryDependencies ++= Seq(guice, playTestDep)
  ).enablePlugins(PlayScala)
