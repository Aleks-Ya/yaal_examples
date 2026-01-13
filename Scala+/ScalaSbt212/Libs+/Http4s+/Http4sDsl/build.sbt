import Dependencies.{http4sDslDep, http4sEmberClientDep, catsEffectScalaTestDep, scalaTestDep, slf4jSimpleDep}

lazy val Http4sDsl = (project in file(".")).settings(
  libraryDependencies ++= Seq(http4sDslDep, http4sEmberClientDep, scalaTestDep, catsEffectScalaTestDep, slf4jSimpleDep)
)
