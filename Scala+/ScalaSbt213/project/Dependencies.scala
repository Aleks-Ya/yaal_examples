import sbt.*

object Dependencies {
  val scalaTestDep = "org.scalatest" %% "scalatest" % "3.2.20" % Test
  val scalaTestJsonAssertDep = "com.stephenn" %% "scalatest-json-jsonassert" % "0.2.5" % Test
  val scalaMockDep = "org.scalamock" %% "scalamock" % "7.5.5" % Test
  val h2Dep = "com.h2database" % "h2" % "2.4.240"
  val logbackClassicDep = "ch.qos.logback" % "logback-classic" % "1.5.32"
  val scoptDep = "com.github.scopt" %% "scopt" % "4.1.0"
  val json4sNativeDep = "org.json4s" %% "json4s-native" % "4.0.7"
  val sprayJsonDep = "io.spray" %% "spray-json" % "1.3.6"
  val jettyServletDep = "org.eclipse.jetty" % "jetty-servlet" % "11.0.26" % Test

  private val testContainersVersion = "0.44.1"
  val testContainersScalaTestDep = "com.dimafeng" %% "testcontainers-scala-scalatest" % testContainersVersion % Test
  val testContainersOpenSearchDep = "com.dimafeng" %% "testcontainers-scala-opensearch" % testContainersVersion % Test

  private val slf4jVersion = "2.0.18"
  val slf4jNopDep = "org.slf4j" % "slf4j-nop" % slf4jVersion
  val slf4jSimpleDep = "org.slf4j" % "slf4j-simple" % slf4jVersion

  private val akkaVersion = "2.8.8"
  val akkaActorTypedDep = "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
  val akkaActorTestKitTypedDep = "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test

  val typesafeConfigDep = "com.typesafe" % "config" % "1.4.8"
  val catsEffectDep = "org.typelevel" %% "cats-effect" % "3.7.0"
  val catsEffectScalaTestDep = "org.typelevel" %% "cats-effect-testing-scalatest" % "1.8.0" % Test
  val fs2CoreDep = "co.fs2" %% "fs2-core" % "3.13.0"
  val tomlDep = "com.indoorvivants" %%  "toml" % "0.3.0"
  val tomlScalaDep = "tech.sparse" %% "toml-scala" % "0.2.2"

  private val jacksonVersion = "2.21.3"
  val jacksonDataformatYamlDep = "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % jacksonVersion
  val jacksonModuleScalaDep = "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion

  private val http4sVersion = "0.23.34"
  val http4sEmberClientDep = "org.http4s" %% "http4s-ember-client" % http4sVersion
  val http4sDslDep = "org.http4s" %% "http4s-dsl" % http4sVersion
  val httpJdkHttpClientDep = "org.http4s" %% "http4s-jdk-http-client" % "0.10.0"

  val jsonUnitDep = "net.javacrumbs.json-unit" % "json-unit" % "5.1.1" % Test

  private val spark3Version = "3.5.8"
  private val sparkEncodersVersion = "0.2.3"
  private val djlVersion = "0.36.0"
  val spark3CoreDep = "org.apache.spark" %% "spark-core" % spark3Version
  val spark3MlDep = "org.apache.spark" %% "spark-mllib" % spark3Version
  val spark3SqlDep = "org.apache.spark" %% "spark-sql" % spark3Version
  val spark3StreamingDep = "org.apache.spark" %% "spark-streaming" % spark3Version
  val spark3Encoders = "io.github.pashashiz" %% "spark3-encoders" % sparkEncodersVersion

  private val spark4Version = "4.1.1"
  val spark4CoreDep = "org.apache.spark" %% "spark-core" % spark4Version
  val spark4MlDep = "org.apache.spark" %% "spark-mllib" % spark4Version
  val spark4SqlDep = "org.apache.spark" %% "spark-sql" % spark4Version
  val spark4StreamingDep = "org.apache.spark" %% "spark-streaming" % spark4Version
  val spark4Encoders = "io.github.pashashiz" %% "spark4-encoders" % sparkEncodersVersion

  val mockWebServerDep = "com.squareup.okhttp3" % "mockwebserver" % "5.3.2" % Test

  private val kafkaVersion = "3.9.2"
  val embeddedKafkaDep = "io.github.embeddedkafka" %% "embedded-kafka" % kafkaVersion % Test
  val sparkSqlKafkaDep = "org.apache.spark" %% "spark-sql-kafka-0-10" % spark3Version
  val sparkStreamingKafkaDep = "org.apache.spark" %% "spark-streaming-kafka-0-10-assembly" % spark3Version
  val kafkaClientsDep = "org.apache.kafka" % "kafka-clients" % kafkaVersion % Provided
  val kafkaDep = "org.apache.kafka" %% "kafka" % kafkaVersion

  private val openSearch2Version = "2.19.5"
  val openSearch2Dep = "org.opensearch" % "opensearch" % openSearch2Version
  val opensearchRestHighLevelClient2Dep = "org.opensearch.client" % "opensearch-rest-high-level-client" % openSearch2Version
  private val openSearch3Version = "3.6.0"
  val opensearchRestHighLevelClient3Dep = "org.opensearch.client" % "opensearch-rest-high-level-client" % openSearch3Version

  val databricksSdkJavaDep = "com.databricks" % "databricks-sdk-java" % "0.109.0"
  val hadoopClientApiDep = "org.apache.hadoop" % "hadoop-client-api" % "3.5.0"
  val databricksDbUtilsScalaDep = "com.databricks" %% "databricks-dbutils-scala" % "0.1.5"

  val awaitilityScalaDep = "org.awaitility" % "awaitility-scala" % "4.3.0"
  val retrySoftwareMillDep = "com.softwaremill.retry" %% "retry" % "0.3.6"
}
