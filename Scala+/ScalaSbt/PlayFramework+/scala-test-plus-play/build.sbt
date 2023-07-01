import Dependencies.scalaTestPlusPlayDep

lazy val scalaTestPlusPlay = (project in file(".")).
  settings(
    name := "scala-test-plus-play",
    run / connectInput := true,
    libraryDependencies ++= Seq(guice, scalaTestPlusPlayDep)
  ).enablePlugins(PlayScala)
