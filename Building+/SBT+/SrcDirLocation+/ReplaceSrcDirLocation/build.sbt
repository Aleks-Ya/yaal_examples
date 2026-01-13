import Dependencies.scalaTest

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.21",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "ReplaceSrcDirLocation",
    libraryDependencies += scalaTest,
    Compile / scalaSource  := baseDirectory.value / "src2",
    Compile / resourceDirectory  := baseDirectory.value / "resources2",
    Test / scalaSource  := baseDirectory.value / "test2",
    Test / resourceDirectory  := baseDirectory.value / "resourcesTest2"
  )
