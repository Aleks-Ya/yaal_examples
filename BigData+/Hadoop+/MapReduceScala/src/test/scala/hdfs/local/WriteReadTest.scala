package hdfs.local

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FSDataInputStream, FSDataOutputStream, FileSystem, Path}
import org.scalatest._

class WriteReadTest extends FlatSpec with Matchers {

  it should "write and read file in local HDFS" in {
    val conf = new Configuration()
    val fs = FileSystem.getLocal(conf)
    val file: Path = new Path("/tmp", "test.txt")
    val text = "hi hadoop!"

    if (fs.exists(file)) {
      fs.delete(file, false)
    }

    val os: FSDataOutputStream = fs.create(file)
    os.writeUTF(text)
    os.close()

    val is: FSDataInputStream = fs.open(file)
    val line = is.readUTF()
    is.close()

    line shouldEqual text
  }
}
