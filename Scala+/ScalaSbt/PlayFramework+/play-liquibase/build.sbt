import Dependencies._

lazy val playLiquibase = (project in file(".")).
  settings(
    name := "play-liquibase",
    connectInput in run := true,
    libraryDependencies ++= Seq(guice, scalaTestPlusPlayDep, h2Dep, playLiquibaseDep, playJdbcDep)
  ).enablePlugins(PlayScala)
