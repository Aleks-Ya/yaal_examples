import Dependencies.{jettyServletDep, scalaTestPlusPlayDep}

lazy val playFrameworkHttps = (project in file(".")).
  settings(
    name := "play-framework-https",
    connectInput in run := true,
    libraryDependencies ++= Seq(guice, scalaTestPlusPlayDep)
  ).enablePlugins(PlayScala)
