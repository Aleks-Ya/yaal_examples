import Dependencies.{scalaTestPlusPlayDep, h2Dep, playJdbcDep}

lazy val playJdbc = (project in file(".")).
  settings(
    name := "play-jdbc",
    run / connectInput := true,
    libraryDependencies ++= Seq(guice, scalaTestPlusPlayDep, h2Dep, playJdbcDep)
  ).enablePlugins(PlayScala)
