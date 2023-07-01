import Dependencies.{playSlickDep, scalaTestPlusPlayDep, h2Dep, playLiquibaseDep, playJdbcDep}

lazy val playSlickLiquibase = (project in file(".")).
  settings(
    name := "play-slick-liquibase",
    run / connectInput := true,
    libraryDependencies ++= Seq(guice, playSlickDep, scalaTestPlusPlayDep, h2Dep, playLiquibaseDep, playJdbcDep)
  ).enablePlugins(PlayScala)
