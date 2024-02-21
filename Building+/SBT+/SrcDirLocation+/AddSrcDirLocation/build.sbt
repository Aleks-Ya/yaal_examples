import Dependencies.*

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.18",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "AddSrcDirLocation",
    libraryDependencies += scalaTest,
    Compile / unmanagedSourceDirectories += baseDirectory.value / "src2",
    Compile / unmanagedResourceDirectories += baseDirectory.value / "resources2",
    Test / unmanagedSourceDirectories += baseDirectory.value / "test2",
    Test / unmanagedResourceDirectories += baseDirectory.value / "resourcesTest2"
  )
