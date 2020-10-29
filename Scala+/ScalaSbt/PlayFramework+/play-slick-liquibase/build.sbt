import Dependencies._

lazy val playSlickLiquibase = (project in file(".")).
  settings(
    name := "play-slick-liquibase",
    connectInput in run := true,
    libraryDependencies ++= Seq(guice, playSlickDep, scalaTestPlusPlayDep, h2Dep, playLiquibaseDep, playJdbcDep)
  ).enablePlugins(PlayScala)
