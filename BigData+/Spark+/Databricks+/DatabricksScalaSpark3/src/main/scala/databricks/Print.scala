package databricks

object Print {
  def printInfo(obj: Object, args: Array[String]): Unit = {
    printClassName(obj)
    printMethodArgs(args)
    printEnvVars()
  }

  def finished(): Unit = println("Finished main")

  private def printClassName(obj: Object): Unit = println(s"Main class name: ${obj.getClass.getName}")

  private def printEnvVars(): Unit = println(s"Env vars: ${System.getenv()}")

  private def printMethodArgs(args: Array[String]): Unit =
    println(s"Main method args: ${args.map(v => if (v != null) "'" + v + "'" else "null").mkString(",")}")
}
