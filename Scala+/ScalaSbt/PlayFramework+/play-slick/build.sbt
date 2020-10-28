import Dependencies._

lazy val playSlick = (project in file(".")).
  settings(
    name := "play-slick",
    connectInput in run := true,
    libraryDependencies ++= Seq(guice, playSlickDep, scalaTestPlusPlayDep, h2Dep)
  ).enablePlugins(PlayScala)
