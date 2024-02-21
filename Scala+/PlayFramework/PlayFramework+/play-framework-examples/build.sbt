import Dependencies.{jettyServletDep, jsonUnitDep, scalaTestPlusPlayDep}

lazy val playFrameworkExamples = (project in file(".")).
  settings(
    name := "play-framework-examples",
    run / connectInput := true,
    libraryDependencies ++= Seq(guice, scalaTestPlusPlayDep, jettyServletDep, jsonUnitDep)
  ).enablePlugins(PlayScala)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "ru.yaal.examples.scala.play.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "ru.yaal.examples.scala.play.binders._"
