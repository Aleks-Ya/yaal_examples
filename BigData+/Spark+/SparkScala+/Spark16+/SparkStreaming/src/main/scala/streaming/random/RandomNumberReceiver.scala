package streaming.random

import org.apache.spark.api.java.StorageLevels
import org.apache.spark.streaming.receiver.Receiver

import scala.util.Random

class RandomNumberReceiver(private val intervalMillis: Long = 1000)
  extends Receiver[Int](StorageLevels.MEMORY_ONLY) {

  private var thread: Thread = _

  override def onStart() {
    thread = new Thread() {
      override def run(): Unit = {
        while (!isInterrupted) {
          store(Random.nextInt(100) - 50)
          Thread.sleep(intervalMillis)
        }
      }
    }
    thread.setDaemon(true)
    thread.start()
  }

  override def onStop(): Unit = {
    if (thread != null) thread.interrupt()
  }
}
