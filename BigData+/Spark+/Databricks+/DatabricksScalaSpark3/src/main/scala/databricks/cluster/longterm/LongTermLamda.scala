package databricks.cluster.longterm

import java.time.{Duration, Instant}

object LongTermLamda {
  def beBusyForSeconds(seconds: Int): Long = {
    println(s"[${seconds}sec] Starting a long-term computations...")
    val finalDuration = Duration.ofMinutes(3)
    println(s"[${seconds}sec] Final duration: $finalDuration")
    val startTime = Instant.now()
    var currentDuration = Duration.ZERO
    while (currentDuration.compareTo(finalDuration) == -1) {
      println(s"[${seconds}sec] Current duration: $currentDuration")
      Thread.sleep(5000)
      currentDuration = Duration.between(startTime, Instant.now())
    }
    println(s"[${seconds}sec] Waiting finished")
    seconds
  }
}
