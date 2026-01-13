import Dependencies._
import sbt.Project

ThisBuild / organization := "ru.yaal.examples.scala"
ThisBuild / version := "1"
ThisBuild / scalaVersion := "2.12.21"

lazy val root: Project = (project in file(".")).settings(name := "ScalaSbt212")
  .aggregate(
    UtilSrc, 
    ScalaCore, 
    ScalaTest,
    ScalaTestJsonAssert,
    ScalaMock, 
    ScalaScopt,
    TestContainersOpenSearch,
    TestContainersScalaTest,
    AkkaActorScalaExamples,
    AkkaQuickstartScala,
    Json4s, 
    JsonUnit,
    SprayJson,
    TypesafeConfig,
    OkHttpMockWebServer,
    CatsEffect,
    Fs2Core,
    TomlScala,
    JacksonDataformatYaml,
    Http4s,
    Http4sDsl,
    Http4sJdkHttpClient,
    Spark3Core,
    Spark3Ml,
    Spark3Sql,
    Spark3SqlKafka,
    Spark3Streaming,
    Spark3StreamingKafka,
    Spark3DjlOnnxRs,
    Spark3DjlPyTorch,
    KafkaScalaCore,
    IoGithubEmbeddedKafka,
    ManubEmbeddedKafka,
    NeuralSearch,
    DatabricksDbUtilsScala,
    DatabricksJavaSdk,
    DbUtils,
    AwaitilityScala,
    RetrySoftwareMill)

def mkp(path: String, deps: ModuleID*): Project = {
  val projectId = path.split("[/+]").last
  var p = Project(projectId, file(path))
  p = if (!path.equalsIgnoreCase("Util+/UtilSrc")) p.dependsOn(ClasspathDependency(UtilSrc, None)) else p
  if (deps.nonEmpty) p.settings(libraryDependencies ++= deps) else p
}

lazy val UtilSrc = mkp("Util+/UtilSrc", scalaTestDep)

lazy val ScalaCore = mkp("ScalaCore", scalaTestDep)

lazy val ScalaTest = mkp("ScalaTest+/ScalaTest", scalaTestDep)
lazy val ScalaTestJsonAssert = mkp("ScalaTest+/ScalaTestJsonAssert", scalaTestJsonAssertDep)
lazy val ScalaMock = mkp("ScalaMock", scalaTestDep, scalaMockDep)
lazy val ScalaScopt = mkp("ScalaScopt", scoptDep, scalaTestDep)
lazy val TestContainersOpenSearch = mkp("TestContainers+/TestContainersOpenSearch",
  scalaTestDep, testContainersScalaTestDep, testContainersOpenSearchDep,
  opensearchRestHighLevelClientDep, logbackClassicDep)
lazy val TestContainersScalaTest = mkp("TestContainers+/TestContainersScalaTest",
  scalaTestDep, testContainersScalaTestDep)

lazy val AkkaActorScalaExamples = mkp("Akka+/AkkaActorScalaExamples", akkaActorTypedDep, akkaActorTestKitTypedDep, logbackClassicDep, scalaTestDep)
lazy val AkkaQuickstartScala = mkp("Akka+/AkkaQuickstartScala", akkaActorTypedDep, akkaActorTestKitTypedDep, logbackClassicDep, scalaTestDep)

lazy val Json4s = mkp("Libs+/JSON+/Json4s", json4sNativeDep, scalaTestDep)
lazy val JsonUnit = mkp("Libs+/JSON+/JsonUnit", jsonUnitDep, scalaTestDep)
lazy val SprayJson = mkp("Libs+/JSON+/SprayJson", sprayJsonDep, scalaTestDep)
lazy val TypesafeConfig = mkp("Libs+/TypesafeConfig", typesafeConfigDep, scalaTestDep).settings(cancelable in Global := true)
lazy val OkHttpMockWebServer = mkp("Libs+/OkHttpMockWebServer", scalaTestDep, mockWebServerDep)
lazy val CatsEffect = mkp("Libs+/CatsEffect", catsEffectDep, scalaTestDep, catsEffectScalaTestDep)
lazy val Fs2Core = mkp("Libs+/Fs2+/Fs2Core", fs2CoreDep, scalaTestDep, catsEffectScalaTestDep)
lazy val TomlScala = mkp("Libs+/TOML+/TomlScala", tomlScalaDep, scalaTestDep)

lazy val JacksonDataformatYaml = mkp("Libs+/YAML+/JacksonDataformatYaml", jacksonModuleScalaDep, jacksonDataformatYamlDep, scalaTestDep)

lazy val Http4s = mkp("Libs+/Http4s+/Http4s", http4sEmberClientDep, scalaTestDep, catsEffectScalaTestDep, logbackClassicDep, mockWebServerDep)
lazy val Http4sDsl = mkp("Libs+/Http4s+/Http4sDsl", http4sDslDep, http4sEmberClientDep, scalaTestDep, catsEffectScalaTestDep, slf4jSimpleDep)
lazy val Http4sJdkHttpClient = mkp("Libs+/Http4s+/Http4sJdkHttpClient", httpJdkHttpClientDep, scalaTestDep, catsEffectScalaTestDep)

lazy val Spark3Core = mkp("Spark+/Spark3+/Spark3Core", spark3CoreDep, scalaTestDep)
lazy val Spark3Ml = mkp("Spark+/Spark3+/Spark3Ml", scalaTestDep, spark3MlDep)
lazy val Spark3Sql = mkp("Spark+/Spark3+/Spark3Sql", spark3SqlDep, scalaTestDep, h2Dep)
lazy val Spark3SqlKafka = mkp("Spark+/Spark3+/Spark3SqlKafka", spark3SqlDep, scalaTestDep, sparkSqlKafkaDep, embeddedKafkaDep)
lazy val Spark3Streaming = mkp("Spark+/Spark3+/Spark3Streaming",
  spark3StreamingDep, scalaTestDep) //Structured Streaming is a part of Spark SQL
lazy val Spark3StreamingKafka = mkp("Spark+/Spark3+/Spark3StreamingKafka",
  spark3StreamingDep, scalaTestDep, sparkStreamingKafkaDep, embeddedKafkaDep)
lazy val Spark3DjlOnnxRs = mkp("Spark+/Spark3+/Spark3Djl+/Spark3DjlOnnxRs",
  scalaTestDep, spark3DjlDep, djlOnnxEngineDep, djlHfTokenizersDep)
lazy val Spark3DjlPyTorch = mkp("Spark+/Spark3+/Spark3Djl+/Spark3DjlPyTorch",
  scalaTestDep, spark3DjlDep, djlPyTorchEngineDep, djlPyTorchModelZooDep)

lazy val KafkaScalaCore = mkp("Kafka+/KafkaScalaCore", scalaTestDep, kafkaClientsDep)
lazy val IoGithubEmbeddedKafka = mkp("Kafka+/EmbeddedKafka+/IoGithubEmbeddedKafka", scalaTestDep, kafkaClientsDep, kafkaDep, embeddedKafkaDep)
lazy val ManubEmbeddedKafka = mkp("Kafka+/EmbeddedKafka+/ManubEmbeddedKafka", scalaTestDep, kafkaClientsDep, kafkaDep, kafkaManubDep)

lazy val NeuralSearch = mkp("Libs+/OpenSearch+/NeuralSearch", scalaTestDep, openSearchDep)

lazy val DatabricksDbUtilsScala = mkp("Libs+/Databricks+/DatabricksDbUtilsScala",
  scalaTestDep, databricksDbUtilsScalaDep,
  hadoopClientApiDep //for `val dbfs: FileSystem = DBUtilsHolder.dbutils.fs.dbfs`
)
lazy val DatabricksJavaSdk = mkp("Libs+/Databricks+/DatabricksJavaSdk", scalaTestDep, databricksSdkJavaDep)
lazy val DbUtils = mkp("Libs+/Databricks+/DbUtils",
  scalaTestDep, dbUtilsApiDep,
  hadoopClientApiDep //for `val dbfs: FileSystem = DBUtilsHolder.dbutils.fs.dbfs`
)

lazy val AwaitilityScala = mkp("Libs+/AwaitilityScala", awaitilityScalaDep, scalaTestDep)
lazy val RetrySoftwareMill = mkp("Libs+/RetrySoftwareMill", retrySoftwareMillDep, scalaTestDep)
