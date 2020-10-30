import sbt.Project

lazy val common = Seq(
  organization := "ru.yaal.examples.scala",
  version := "1",
  scalaVersion := "2.12.12"
)

lazy val root: Project = (project in file(".")).settings(common, name := "ScalaSbt")
  .aggregate(ScalaCore, ScalaTest, ScalaMock, ScalaScopt, json4s, Slick, akkaActorScalaExamples, akkaQuickScala,
    playFrameworkExamples, scalaTestPlusPlay, playTest, playSlick, playLiquibase, playSlickLiquibase)

lazy val ScalaCore = (project in file("ScalaCore")).settings(common)
lazy val ScalaTest = (project in file("ScalaTest")).settings(common)
lazy val ScalaMock = (project in file("ScalaMock")).settings(common)
lazy val ScalaScopt = (project in file("ScalaScopt")).settings(common)
lazy val json4s = (project in file("json4s")).settings(common)
lazy val Slick = (project in file("Slick")).settings(common)
lazy val akkaActorScalaExamples = (project in file("Akka+/akka-actor-scala-examples")).settings(common)
lazy val akkaQuickScala = (project in file("Akka+/akka-quickstart-scala")).settings(common)
lazy val playFrameworkExamples = (project in file("PlayFramework+/play-framework-examples")).settings(common)
lazy val scalaTestPlusPlay = (project in file("PlayFramework+/scala-test-plus-play")).settings(common)
lazy val playTest = (project in file("PlayFramework+/play-test")).settings(common)
lazy val playSlick = (project in file("PlayFramework+/play-slick")).settings(common)
lazy val playLiquibase = (project in file("PlayFramework+/play-liquibase")).settings(common)
lazy val playJdbc = (project in file("PlayFramework+/play-jdbc")).settings(common)
lazy val playSlickLiquibase = (project in file("PlayFramework+/play-slick-liquibase")).settings(common)
