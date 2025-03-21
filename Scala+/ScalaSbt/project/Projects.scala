import sbt.{file, project}

object Projects {
  lazy val UtilSrc = project in file("Util+/UtilSrc")

  lazy val ScalaCore = project in file("ScalaCore")

  lazy val ScalaTest = project in file("ScalaTest+/ScalaTest")
  lazy val ScalaTestJsonAssert = project in file("ScalaTest+/ScalaTestJsonAssert")
  lazy val ScalaMock = project in file("ScalaMock")
  lazy val ScalaRun = project in file("ScalaRun")
  lazy val ScalaScopt = project in file("ScalaScopt")

  lazy val AkkaActorScalaExamples = project in file("Akka+/AkkaActorScalaExamples")
  lazy val AkkaQuickstartScala = project in file("Akka+/AkkaQuickstartScala")

  lazy val Json4s = project in file("Libs+/JSON+/Json4s")
  lazy val JsonUnit = project in file("Libs+/JSON+/JsonUnit")
  lazy val SprayJson = project in file("Libs+/JSON+/SprayJson")
  lazy val TypesafeConfig = project in file("Libs+/TypesafeConfig")
  lazy val OkHttpMockWebServer = project in file("Libs+/OkHttpMockWebServer")
  lazy val CatsEffect = project in file("Libs+/CatsEffect")
  lazy val Fs2Core = project in file("Libs+/Fs2+/Fs2Core")
  lazy val TomlScala = project in file("Libs+/TOML+/TomlScala")

  lazy val JacksonDataformatYaml = project in file("Libs+/YAML+/JacksonDataformatYaml")

  lazy val Http4s = project in file("Libs+/Http4s+/Http4s")
  lazy val Http4sDsl = project in file("Libs+/Http4s+/Http4sDsl")
  lazy val Http4sJdkHttpClient = project in file("Libs+/Http4s+/Http4sJdkHttpClient")

  lazy val Spark3Core = project in file("Spark+/Spark3+/Spark3Core")
  lazy val Spark3Ml = project in file("Spark+/Spark3+/Spark3Ml")
  lazy val Spark3Sql = project in file("Spark+/Spark3+/Spark3Sql")
  lazy val Spark3Streaming = project in file("Spark+/Spark3+/Spark3Streaming")

  lazy val KafkaScalaCore = project in file("Kafka+/KafkaScalaCore")
  lazy val IoGithubEmbeddedKafka = project in file("Kafka+/EmbeddedKafka+/IoGithubEmbeddedKafka")
  lazy val ManubEmbeddedKafka = project in file("Kafka+/EmbeddedKafka+/ManubEmbeddedKafka")

  lazy val NeuralSearch = project in file("Libs+/OpenSearch+/NeuralSearch")
}
