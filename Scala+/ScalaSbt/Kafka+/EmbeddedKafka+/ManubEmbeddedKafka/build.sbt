import Dependencies.{kafkaClientsDep, kafkaDep, kafkaManubDep, scalaTestDep}

lazy val ManubEmbeddedKafka = (project in file("."))
  .settings(libraryDependencies ++= Seq(scalaTestDep, kafkaClientsDep, kafkaDep, kafkaManubDep))