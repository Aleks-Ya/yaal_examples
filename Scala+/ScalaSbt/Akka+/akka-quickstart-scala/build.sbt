import Dependencies.{akkaActorTestKitTypedDep, akkaActorTypedDep, logbackClassicDep, scalaTestDep}
//Source: https://developer.lightbend.com/guides/akka-quickstart-scala/

lazy val akkaQuickScala = (project in file(".")).
  settings(
    name := "akka-quickstart-scala",
    libraryDependencies ++= Seq(akkaActorTypedDep, akkaActorTestKitTypedDep, logbackClassicDep, scalaTestDep)
  )
