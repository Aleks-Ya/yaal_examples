import Dependencies.{djlOnnxEngineDep, djlHfTokenizersDep, scalaTestDep, spark3DjlDep}

lazy val Spark3DjlOnnxRs = (project in file("."))
  .dependsOn(Projects.UtilSrc)
  .settings(libraryDependencies ++= Seq(scalaTestDep, spark3DjlDep, djlOnnxEngineDep, djlHfTokenizersDep))
