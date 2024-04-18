import Dependencies.{catsEffectScalaTestDep, httpJdkHttpClientDep, scalaTestDep}

lazy val Http4sJdkHttpClient = (project in file(".")).settings(
  libraryDependencies ++= Seq(httpJdkHttpClientDep, scalaTestDep, catsEffectScalaTestDep)
)
