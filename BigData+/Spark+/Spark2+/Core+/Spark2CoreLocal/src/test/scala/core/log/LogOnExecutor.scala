package core.log

import core.Factory
import org.scalatest.{FlatSpec, Matchers}

class LogOnExecutor extends FlatSpec with Matchers {

  it should "log from mapper" in {
    class Mapper extends Serializable {
      private lazy val log = org.apache.log4j.LogManager.getLogger(this.getClass)

      def processNumber(number: Int): Int = {
        log.info(s"Log from Mapper: $number")
        number
      }
    }

    val mapper = new Mapper
    val data = Seq(1, 2, 3)
    Factory.sc
      .parallelize(data)
      .map(mapper.processNumber)
      .collect
  }

}
