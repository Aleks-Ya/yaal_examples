import Dependencies.{scalaTestPlusPlayDep, h2Dep, playLiquibaseDep, playJdbcDep}

lazy val playLiquibase = (project in file(".")).
  settings(
    name := "play-liquibase",
    run / connectInput := true,
    libraryDependencies ++= Seq(guice, scalaTestPlusPlayDep, h2Dep, playLiquibaseDep, playJdbcDep)
  ).enablePlugins(PlayScala)
