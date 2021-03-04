import Dependencies.scalaTestPlusPlayDep

lazy val playFrameworkHttps = (project in file(".")).
  settings(
    name := "play-framework-https",
    connectInput in run := true,
    libraryDependencies ++= Seq(guice, scalaTestPlusPlayDep),
    cancelable in Global := true,
    fork in run := true
  ).enablePlugins(PlayScala)
