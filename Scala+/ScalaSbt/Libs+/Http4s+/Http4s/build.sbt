import Dependencies.{catsEffectScalaTestDep, http4sEmberClientDep, logbackClassicDep, scalaTestDep}

lazy val Http4s = (project in file(".")).settings(
  libraryDependencies ++= Seq(http4sEmberClientDep, scalaTestDep, catsEffectScalaTestDep, logbackClassicDep)
)
