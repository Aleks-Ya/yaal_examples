package shared_variables.broadcast.accumulator_in_broadcast_variable

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.util.LongAccumulator

object UpperCaseLambda {
  def toUppercase(word: String, dataBroadcast: Broadcast[Data], counter: LongAccumulator): String = {
    counter.add(1)
    dataBroadcast.value.replace(word)
  }
}
