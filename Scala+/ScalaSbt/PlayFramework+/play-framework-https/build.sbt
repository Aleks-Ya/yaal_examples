import Dependencies.scalaTestPlusPlayDep

lazy val playFrameworkHttps = (project in file(".")).
  settings(
    name := "play-framework-https",
    run / connectInput := true,
    libraryDependencies ++= Seq(guice, scalaTestPlusPlayDep),
    Global / cancelable := true,
    run / fork := true
  ).enablePlugins(PlayScala)
