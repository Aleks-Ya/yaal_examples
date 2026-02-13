import sbt.*

object Dependencies {
  private val sparkVersion = "3.5.1"
  val allDeps: Seq[ModuleID] = Seq(
    "org.apache.spark" %% "spark-sql-api" % sparkVersion,
    "org.apache.spark" %% "spark-connect-client-jvm" % sparkVersion,

    //Fix "NoSuchMethodError: com.google.common.base.Preconditions.checkArgument(ZLjava/lang/String;CLjava/lang/Object;)V"
    "com.google.guava" % "guava" % "33.1.0-jre"
  )
}
