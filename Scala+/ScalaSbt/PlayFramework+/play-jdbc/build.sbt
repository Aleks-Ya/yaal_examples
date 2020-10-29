import Dependencies._

lazy val playJdbc = (project in file(".")).
  settings(
    name := "play-jdbc",
    connectInput in run := true,
    libraryDependencies ++= Seq(guice, scalaTestPlusPlayDep, h2Dep, playJdbcDep)
  ).enablePlugins(PlayScala)
