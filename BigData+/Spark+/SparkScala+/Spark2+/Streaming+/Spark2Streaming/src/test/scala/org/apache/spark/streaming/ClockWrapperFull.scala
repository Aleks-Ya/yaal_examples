package org.apache.spark.streaming

import org.apache.spark.util.ManualClock

object ClockWrapperFull {
  private var clock: ManualClock = _

  def setSparkStreamingContext(ssc: StreamingContext): Unit = {
    clock = ssc.scheduler.clock.asInstanceOf[ManualClock]
  }

  def getTimeMillis: Long = clock.getTimeMillis()

  def setTime(timeToSet: Long): Unit = clock.setTime(timeToSet)

  def advance(timeToAdd: Long): Unit = clock.advance(timeToAdd)

  def waitTillTime(targetTime: Long): Long = clock.waitTillTime(targetTime)

}
