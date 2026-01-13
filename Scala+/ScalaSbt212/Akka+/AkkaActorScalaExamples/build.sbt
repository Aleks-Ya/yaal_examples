import Dependencies.{akkaActorTestKitTypedDep, akkaActorTypedDep, logbackClassicDep, scalaTestDep}

lazy val AkkaActorScalaExamples = (project in file(".")).
  settings(libraryDependencies ++= Seq(akkaActorTypedDep, akkaActorTestKitTypedDep, logbackClassicDep, scalaTestDep))