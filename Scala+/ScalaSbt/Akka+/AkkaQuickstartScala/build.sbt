import Dependencies.{akkaActorTestKitTypedDep, akkaActorTypedDep, logbackClassicDep, scalaTestDep}
//Source: https://developer.lightbend.com/guides/akka-quickstart-scala/

lazy val AkkaQuickstartScala = (project in file(".")).
  settings(libraryDependencies ++= Seq(akkaActorTypedDep, akkaActorTestKitTypedDep, logbackClassicDep, scalaTestDep))
