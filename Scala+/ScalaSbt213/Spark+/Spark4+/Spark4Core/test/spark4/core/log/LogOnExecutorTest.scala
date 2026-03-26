package spark4.core.log

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import spark4.core.Factory

class LogOnExecutorTest extends AnyFlatSpec with Matchers {

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
