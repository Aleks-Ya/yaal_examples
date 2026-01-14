package run

/**
 * Run once:<br/>
 * <code>sbt "project ScalaRun" run</code><br/>
 * <code>sbt> ;project ScalaRun; run</code>
 *
 * Stop: Ctrl-C
 *
 * Run with recompile and restart after Ctrl-C:<br/>
 * * <code>sbt "project ScalaRun" ~run</code><br/>
 * * <code>sbt> ;project ScalaRun; ~run</code>
 */
object ReloadMain {
  private var n = 0

  def main(args: Array[String]): Unit = {
    while (true) {
      println(getNum)
      Thread.sleep(1000)
    }
  }

  private def getNum = {
    n = n + 1
    n
  }
}
