import sbt._

object Dependencies {
  val scalaTestDep = "org.scalatest" %% "scalatest" % "3.2.12" % Test
  val scalaMockDep = "org.scalamock" %% "scalamock" % "5.1.0" % Test
  val h2Dep = "com.h2database" % "h2" % "1.4.200"
  val logbackClassicDep = "ch.qos.logback" % "logback-classic" % "1.2.3"
  val scoptDep = "com.github.scopt" %% "scopt" % "4.0.0"
  val json4sNativeDep = "org.json4s" %% "json4s-native" % "3.6.10"
  val sprayJsonDep = "io.spray" %% "spray-json" % "1.3.6"
  val jettyServletDep = "org.eclipse.jetty" % "jetty-servlet" % "9.4.36.v20210114" % Test

  private val slf4jVersion = "1.7.36"
  val slf4jNopDep = "org.slf4j" % "slf4j-nop" % slf4jVersion
  val slf4jSimpleDep = "org.slf4j" % "slf4j-simple" % slf4jVersion

  private val akkaVersion = "2.6.16"
  val akkaActorTypedDep = "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
  val akkaActorTestKitTypedDep = "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test

  private val slickVersion = "3.3.3"
  private val playVersion = "2.8.15"
  val slickDep = "com.typesafe.slick" %% "slick" % slickVersion
  val slickHikaricpDep = "com.typesafe.slick" %% "slick-hikaricp" % slickVersion
  val slickTestkitDep = "com.typesafe.slick" %% "slick-testkit" % slickVersion % Test
  val playLiquibaseDep = "com.ticketfly" %% "play-liquibase" % "2.2"
  val playSlickDep = "com.typesafe.play" %% "play-slick" % "5.0.0"
  val playJdbcDep = "com.typesafe.play" %% "play-jdbc" % playVersion
  val playTestDep = "com.typesafe.play" %% "play-test" % playVersion
  val scalaTestPlusPlayDep = "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test

  val typesafeConfigDep = "com.typesafe" % "config" % "1.4.1"

  val jsonUnitDep = "net.javacrumbs.json-unit" % "json-unit" % "2.24.0" % Test

  private val spark3Version = "3.2.1"
  val spark3CoreDep = "org.apache.spark" %% "spark-core" % spark3Version
  val spark3SqlDep = "org.apache.spark" %% "spark-sql" % spark3Version
  val spark3StreamingDep = "org.apache.spark" %% "spark-streaming" % spark3Version

  val mockWebServerDep = "com.squareup.okhttp3" % "mockwebserver" % "4.9.1" % Test

  private val kafkaVersion = "3.2.0"
  val embeddedKafkaDep = "io.github.embeddedkafka" %% "embedded-kafka" % kafkaVersion % Test
  val sparkStreamingKafkaDep = "org.apache.spark" %% "spark-streaming-kafka-0-10-assembly" % spark3Version
  val kafkaClientsDep = "org.apache.kafka" % "kafka-clients" % kafkaVersion % Provided
  val kafkaDep = "org.apache.kafka" %% "kafka" % kafkaVersion
  val kafkaManubDep = "net.manub" %% "scalatest-embedded-kafka" % "2.0.0" % Test
}
