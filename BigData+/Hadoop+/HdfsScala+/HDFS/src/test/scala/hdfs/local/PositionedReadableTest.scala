package hdfs.local

import org.apache.hadoop.fs.FSDataInputStream
import org.scalatest._

class PositionedReadableTest extends FlatSpec with Matchers {

  it should "read part of file" in {
    val file = Factory.writeTmpFile("PositionedReadableTest.txt", "just do it")

    val is: FSDataInputStream = Factory.fs.open(file)

    val buffer = Array.ofDim[Byte](2)
    is.read(7, buffer, 0, 2)
    new String(buffer) shouldEqual "do"

    is.read(7, buffer, 0, 2)
    new String(buffer) shouldEqual "do"

    is.close()
  }
}
