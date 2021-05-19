import sbt.Project

ThisBuild / organization := "ru.yaal.examples.scala"
ThisBuild / version      := "1"
ThisBuild / scalaVersion := "2.12.13"

lazy val root: Project = (project in file(".")).settings(name := "ScalaSbt")
  .aggregate(ScalaCore, ScalaTest, ScalaMock, ScalaScopt, json4s, Slick, akkaActorScalaExamples, akkaQuickScala,
    playFrameworkExamples, scalaTestPlusPlay, playTest, playSlick, playLiquibase, playJdbc, playSlickLiquibase)

lazy val ScalaCore = project in file("ScalaCore")
lazy val ScalaTest = project in file("ScalaTest")
lazy val ScalaMock = project in file("ScalaMock")
lazy val ScalaRun = project in file("ScalaRun")
lazy val ScalaScopt = project in file("ScalaScopt")
lazy val json4s = project in file("json4s")
lazy val Slick = project in file("Slick")
lazy val akkaActorScalaExamples = project in file("Akka+/akka-actor-scala-examples")
lazy val akkaQuickScala = project in file("Akka+/akka-quickstart-scala")
lazy val playFrameworkExamples = project in file("PlayFramework+/play-framework-examples")
lazy val playFrameworkHttps = project in file("PlayFramework+/play-framework-https")
lazy val scalaTestPlusPlay = project in file("PlayFramework+/scala-test-plus-play")
lazy val playTest = project in file("PlayFramework+/play-test")
lazy val playSlick = project in file("PlayFramework+/play-slick")
lazy val playLiquibase = project in file("PlayFramework+/play-liquibase")
lazy val playJdbc = project in file("PlayFramework+/play-jdbc")
lazy val playSlickLiquibase = project in file("PlayFramework+/play-slick-liquibase")
lazy val TypesafeConfig = project in file("Libs+/TypesafeConfig")
lazy val OkHttpMockWebServer = project in file("Libs+/OkHttpMockWebServer")
lazy val Spark3Core = project in file("Spark+/Spark3+/Spark3Core")
lazy val Spark3Sql = project in file("Spark+/Spark3+/Spark3Sql")
lazy val Spark3Streaming = project in file("Spark+/Spark3+/Spark3Streaming")
