import Dependencies.{djlPyTorchEngineDep, djlPyTorchModelZooDep, scalaTestDep, spark3DjlDep}

lazy val Spark3DjlPyTorch = (project in file("."))
  .dependsOn(Projects.UtilSrc)
  .settings(libraryDependencies ++= Seq(scalaTestDep, spark3DjlDep, djlPyTorchEngineDep, djlPyTorchModelZooDep))
