import sbt.*

object Dependencies {
  val scalaTestDep = "org.scalatest" %% "scalatest" % "3.2.18" % Test
  val scalaMockDep = "org.scalamock" %% "scalamock" % "5.2.0" % Test
  val h2Dep = "com.h2database" % "h2" % "2.2.224"
  val logbackClassicDep = "ch.qos.logback" % "logback-classic" % "1.5.3"
  val scoptDep = "com.github.scopt" %% "scopt" % "4.1.0"
  val json4sNativeDep = "org.json4s" %% "json4s-native" % "4.0.7"
  val sprayJsonDep = "io.spray" %% "spray-json" % "1.3.6"
  val jettyServletDep = "org.eclipse.jetty" % "jetty-servlet" % "11.0.18" % Test

  private val slf4jVersion = "2.0.12"
  val slf4jNopDep = "org.slf4j" % "slf4j-nop" % slf4jVersion
  val slf4jSimpleDep = "org.slf4j" % "slf4j-simple" % slf4jVersion

  private val akkaVersion = "2.8.5"
  val akkaActorTypedDep = "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
  val akkaActorTestKitTypedDep = "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test

  val typesafeConfigDep = "com.typesafe" % "config" % "1.4.3"
  val catsEffectDep = "org.typelevel" %% "cats-effect" % "3.5.3"
  val catsEffectScalaTestDep = "org.typelevel" %% "cats-effect-testing-scalatest" % "1.5.0" % Test

  val jsonUnitDep = "net.javacrumbs.json-unit" % "json-unit" % "3.2.7" % Test

  private val spark3Version = "3.5.1"
  val spark3CoreDep = "org.apache.spark" %% "spark-core" % spark3Version
  val spark3SqlDep = "org.apache.spark" %% "spark-sql" % spark3Version
  val spark3StreamingDep = "org.apache.spark" %% "spark-streaming" % spark3Version

  val mockWebServerDep = "com.squareup.okhttp3" % "mockwebserver" % "4.11.0" % Test

  private val kafkaVersion = "3.5.1"
  val embeddedKafkaDep = "io.github.embeddedkafka" %% "embedded-kafka" % kafkaVersion % Test
  val sparkStreamingKafkaDep = "org.apache.spark" %% "spark-streaming-kafka-0-10-assembly" % spark3Version
  val kafkaClientsDep = "org.apache.kafka" % "kafka-clients" % kafkaVersion % Provided
  val kafkaDep = "org.apache.kafka" %% "kafka" % kafkaVersion
  val kafkaManubDep = "net.manub" %% "scalatest-embedded-kafka" % "2.0.0" % Test
}
