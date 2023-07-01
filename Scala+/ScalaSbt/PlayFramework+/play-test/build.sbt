import Dependencies.playTestDep

lazy val playTest = (project in file(".")).
  settings(
    name := "play-test",
    run / connectInput := true,
    libraryDependencies ++= Seq(guice, playTestDep)
  ).enablePlugins(PlayScala)
