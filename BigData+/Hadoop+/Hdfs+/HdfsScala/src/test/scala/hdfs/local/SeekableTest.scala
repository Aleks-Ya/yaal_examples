package hdfs.local

import org.apache.hadoop.fs.FSDataInputStream
import org.scalatest._

class SeekableTest extends FlatSpec with Matchers {

  it should "read file twice using random access" in {
    val content = "read me twice"
    val file = Factory.writeTmpFile("text.txt", content)

    val is: FSDataInputStream = Factory.fs.open(file)
    val line = is.readUTF()
    is.seek(0)
    val line2 = is.readUTF()
    is.close()

    line shouldEqual content
    line2 shouldEqual content
  }
}
