import sbt.Project

ThisBuild / organization := "ru.yaal.examples.scala"
ThisBuild / version      := "1"
ThisBuild / scalaVersion := "2.12.19"

lazy val root: Project = (project in file(".")).settings(name := "ScalaSbt")
  .aggregate(ScalaCore, ScalaTest, ScalaMock, ScalaScopt, json4s, akkaActorScalaExamples, akkaQuickScala)

lazy val ScalaCore = project in file("ScalaCore")
lazy val ScalaTest = project in file("ScalaTest+/ScalaTest")
lazy val ScalaTestJsonAssert = project in file("ScalaTest+/ScalaTestJsonAssert")
lazy val ScalaMock = project in file("ScalaMock")
lazy val ScalaRun = project in file("ScalaRun")
lazy val ScalaScopt = project in file("ScalaScopt")

lazy val akkaActorScalaExamples = project in file("Akka+/akka-actor-scala-examples")
lazy val akkaQuickScala = project in file("Akka+/akka-quickstart-scala")

lazy val json4s = project in file("Libs+/JSON+/json4s")
lazy val SprayJson = project in file("Libs+/JSON+/SprayJson")
lazy val TypesafeConfig = project in file("Libs+/TypesafeConfig")
lazy val OkHttpMockWebServer = project in file("Libs+/OkHttpMockWebServer")
lazy val CatsEffect = project in file("Libs+/CatsEffect")

lazy val Spark3Core = project in file("Spark+/Spark3+/Spark3Core")
lazy val Spark3Sql = project in file("Spark+/Spark3+/Spark3Sql")
lazy val Spark3Streaming = project in file("Spark+/Spark3+/Spark3Streaming")

lazy val KafkaScalaCore = project in file("Kafka+/KafkaScalaCore")
lazy val IoGithubEmbeddedKafka = project in file("Kafka+/EmbeddedKafka+/IoGithubEmbeddedKafka")
lazy val ManubEmbeddedKafka = project in file("Kafka+/EmbeddedKafka+/ManubEmbeddedKafka")
