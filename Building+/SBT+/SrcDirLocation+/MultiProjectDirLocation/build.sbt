/*
 * SBT Multi Project build.
 * Docs: https://www.scala-sbt.org/1.x/docs/Multi-Project.html
 */

import Dependencies.scalaTest

lazy val commonBuildSettings = Seq(
  organization := "com.example",
  scalaVersion := "2.12.21",
  version := "0.1.0-SNAPSHOT"
)
inThisBuild(commonBuildSettings)

lazy val commonSettings = Seq(
  libraryDependencies += scalaTest,
  Compile / scalaSource := baseDirectory.value / "src2",
  Compile / resourceDirectory := baseDirectory.value / "resources2",
  Test / scalaSource := baseDirectory.value / "test2",
  Test / resourceDirectory := baseDirectory.value / "resourcesTest2"
)

lazy val root: Project = (project in file("."))
  .settings(commonSettings)
  .settings(name := "MultiProjectDirLocation")
  .aggregate(subProjectA, subProjectB)


lazy val subProjectA: Project = (project in file("SubProjectA")).settings(commonSettings)

lazy val subProjectB: Project = (project in file("SubProjectB")).settings(commonSettings)
