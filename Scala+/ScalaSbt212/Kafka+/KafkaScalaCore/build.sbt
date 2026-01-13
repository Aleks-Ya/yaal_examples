import Dependencies.{kafkaClientsDep, kafkaDep, scalaTestDep}

lazy val KafkaScalaCore = (project in file("."))
  .settings(libraryDependencies ++= Seq(scalaTestDep, kafkaClientsDep))