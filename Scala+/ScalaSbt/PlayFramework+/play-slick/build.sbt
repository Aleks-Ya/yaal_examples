import Dependencies.{playSlickDep, scalaTestPlusPlayDep, h2Dep}

lazy val playSlick = (project in file(".")).
  settings(
    name := "play-slick",
    run / connectInput := true,
    libraryDependencies ++= Seq(guice, playSlickDep, scalaTestPlusPlayDep, h2Dep)
  ).enablePlugins(PlayScala)
