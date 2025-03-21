import sbt.*

object Dependencies {
  val scalaTestDep = "org.scalatest" %% "scalatest" % "3.2.19" % Test
  val scalaMockDep = "org.scalamock" %% "scalamock" % "6.2.0" % Test
  val h2Dep = "com.h2database" % "h2" % "2.2.224"
  val logbackClassicDep = "ch.qos.logback" % "logback-classic" % "1.5.17"
  val scoptDep = "com.github.scopt" %% "scopt" % "4.1.0"
  val json4sNativeDep = "org.json4s" %% "json4s-native" % "4.0.7"
  val sprayJsonDep = "io.spray" %% "spray-json" % "1.3.6"
  val jettyServletDep = "org.eclipse.jetty" % "jetty-servlet" % "11.0.22" % Test

  private val slf4jVersion = "2.0.17"
  val slf4jNopDep = "org.slf4j" % "slf4j-nop" % slf4jVersion
  val slf4jSimpleDep = "org.slf4j" % "slf4j-simple" % slf4jVersion

  private val akkaVersion = "2.8.6"
  val akkaActorTypedDep = "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
  val akkaActorTestKitTypedDep = "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test

  val typesafeConfigDep = "com.typesafe" % "config" % "1.4.3"
  val catsEffectDep = "org.typelevel" %% "cats-effect" % "3.5.7"
  val catsEffectScalaTestDep = "org.typelevel" %% "cats-effect-testing-scalatest" % "1.6.0" % Test
  val fs2CoreDep = "co.fs2" %% "fs2-core" % "3.11.0"
  val tomlScalaDep = "tech.sparse" %% "toml-scala" % "0.2.2"

  private val jacksonVersion = "2.18.3"
  val jacksonDataformatYamlDep = "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % jacksonVersion
  val jacksonModuleScalaDep = "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion

  private val http4sVersion = "0.23.30"
  val http4sEmberClientDep = "org.http4s" %% "http4s-ember-client" % http4sVersion
  val http4sDslDep = "org.http4s" %% "http4s-dsl" % http4sVersion
  val httpJdkHttpClientDep = "org.http4s" %% "http4s-jdk-http-client" % "0.10.0"

  val jsonUnitDep = "net.javacrumbs.json-unit" % "json-unit" % "4.1.0" % Test

  private val spark3Version = "3.5.5"
  val spark3CoreDep = "org.apache.spark" %% "spark-core" % spark3Version
  val spark3MlDep = "org.apache.spark" %% "spark-mllib" % spark3Version
  val spark3SqlDep = "org.apache.spark" %% "spark-sql" % spark3Version
  val spark3StreamingDep = "org.apache.spark" %% "spark-streaming" % spark3Version

  val mockWebServerDep = "com.squareup.okhttp3" % "mockwebserver" % "4.12.0" % Test

  private val kafkaVersion = "3.9.0"
  val embeddedKafkaDep = "io.github.embeddedkafka" %% "embedded-kafka" % kafkaVersion % Test
  val sparkSqlKafkaDep = "org.apache.spark" %% "spark-sql-kafka-0-10" % spark3Version
  val sparkStreamingKafkaDep = "org.apache.spark" %% "spark-streaming-kafka-0-10-assembly" % spark3Version
  val kafkaClientsDep = "org.apache.kafka" % "kafka-clients" % kafkaVersion % Provided
  val kafkaDep = "org.apache.kafka" %% "kafka" % kafkaVersion
  val kafkaManubDep = "net.manub" %% "scalatest-embedded-kafka" % "2.0.0" % Test

  val opensearchDep = "org.opensearch" % "opensearch" % "2.17.1"
}
