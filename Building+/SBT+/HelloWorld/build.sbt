import Dependencies.*

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.18",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "HelloWorld",
    libraryDependencies += scalaTest
  )
