import Dependencies.{http4sDep, httpJdkHttpClientDep, scalaTestDep, catsEffectScalaTestDep}

lazy val Http4s = (project in file(".")).settings(
  libraryDependencies ++= Seq(http4sDep, httpJdkHttpClientDep, scalaTestDep, catsEffectScalaTestDep)
)
