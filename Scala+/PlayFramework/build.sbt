import sbt.Project

ThisBuild / organization := "ru.yaal.examples.scala"
ThisBuild / version := "1"
ThisBuild / scalaVersion := "2.12.18"

lazy val root: Project = (project in file(".")).settings(name := "PlayFramework")
  .aggregate(Slick, playFrameworkExamples, scalaTestPlusPlay, playTest, playSlick, playLiquibase, playJdbc, playSlickLiquibase)

lazy val Slick = project in file("Slick")

lazy val playFrameworkExamples = project in file("PlayFramework+/play-framework-examples")
lazy val playFrameworkHttps = project in file("PlayFramework+/play-framework-https")
lazy val scalaTestPlusPlay = project in file("PlayFramework+/scala-test-plus-play")
lazy val playTest = project in file("PlayFramework+/play-test")
lazy val playSlick = project in file("PlayFramework+/play-slick")
lazy val playLiquibase = project in file("PlayFramework+/play-liquibase")
lazy val playJdbc = project in file("PlayFramework+/play-jdbc")
lazy val playSlickLiquibase = project in file("PlayFramework+/play-slick-liquibase")
