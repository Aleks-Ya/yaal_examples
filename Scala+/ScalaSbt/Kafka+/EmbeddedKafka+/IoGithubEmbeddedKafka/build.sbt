import Dependencies.{embeddedKafkaDep, kafkaClientsDep, kafkaDep, scalaTestDep}

lazy val IoGithubEmbeddedKafka = (project in file("."))
  .settings(libraryDependencies ++= Seq(scalaTestDep, kafkaClientsDep, kafkaDep, embeddedKafkaDep))
