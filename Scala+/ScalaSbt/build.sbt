import sbt.Project

ThisBuild / organization := "ru.yaal.examples.scala"
ThisBuild / version := "1"
ThisBuild / scalaVersion := "2.12.20"

lazy val root: Project = (project in file(".")).settings(name := "ScalaSbt")
  .aggregate(ScalaCore, ScalaTest, ScalaMock, ScalaScopt, Json4s, JsonUnit, AkkaActorScalaExamples, AkkaQuickstartScala,
    Http4s, Http4sJdkHttpClient, UtilSrc)

lazy val UtilSrc = Projects.UtilSrc

lazy val ScalaCore = Projects.ScalaCore

lazy val ScalaTest = Projects.ScalaTest
lazy val ScalaTestJsonAssert = Projects.ScalaTestJsonAssert
lazy val ScalaMock = Projects.ScalaMock
lazy val ScalaRun = Projects.ScalaRun
lazy val ScalaScopt = Projects.ScalaScopt
lazy val TestContainersScalaTest = Projects.TestContainersScalaTest
lazy val TestContainersOpenSearch = Projects.TestContainersOpenSearch

lazy val AkkaActorScalaExamples = Projects.AkkaActorScalaExamples
lazy val AkkaQuickstartScala = Projects.AkkaQuickstartScala

lazy val Json4s = Projects.Json4s
lazy val JsonUnit = Projects.JsonUnit
lazy val SprayJson = Projects.SprayJson
lazy val TypesafeConfig = Projects.TypesafeConfig
lazy val OkHttpMockWebServer = Projects.OkHttpMockWebServer
lazy val CatsEffect = Projects.CatsEffect
lazy val Fs2Core = Projects.Fs2Core
lazy val TomlScala = Projects.TomlScala

lazy val JacksonDataformatYaml = Projects.JacksonDataformatYaml

lazy val Http4s = Projects.Http4s
lazy val Http4sDsl = Projects.Http4sDsl
lazy val Http4sJdkHttpClient = Projects.Http4sJdkHttpClient

lazy val Spark3Core = Projects.Spark3Core
lazy val Spark3Ml = Projects.Spark3Ml
lazy val Spark3Sql = Projects.Spark3Sql
lazy val Spark3SqlKafka = Projects.Spark3SqlKafka
lazy val Spark3Streaming = Projects.Spark3Streaming
lazy val Spark3StreamingKafka = Projects.Spark3StreamingKafka
lazy val Spark3DjlPyTorch = Projects.Spark3DjlPyTorch
lazy val Spark3DjlOnnxRs = Projects.Spark3DjlOnnxRs

lazy val KafkaScalaCore = Projects.KafkaScalaCore
lazy val IoGithubEmbeddedKafka = Projects.IoGithubEmbeddedKafka
lazy val ManubEmbeddedKafka = Projects.ManubEmbeddedKafka

lazy val NeuralSearch = Projects.NeuralSearch

lazy val DatabricksJavaSdk = Projects.DatabricksJavaSdk
lazy val DbUtils = Projects.DbUtils
lazy val DatabricksDbUtilsScala = Projects.DatabricksDbUtilsScala
