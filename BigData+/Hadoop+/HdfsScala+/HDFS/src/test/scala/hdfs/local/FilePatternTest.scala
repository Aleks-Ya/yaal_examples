package hdfs.local

import hdfs.local.Factory.fs
import org.apache.hadoop.fs.Path
import org.scalatest._

class FilePatternTest extends FlatSpec with Matchers {

  it should "read file twice using random access" in {
    fs.create(new Path("/tmp/FilePatternTest/t1.txt"))
    fs.create(new Path("/tmp/FilePatternTest/t2.txt"))
    fs.create(new Path("/tmp/FilePatternTest/a1.txt"))
    val list = fs.globStatus(new Path("/tmp/FilePatternTest/t*.txt"))
    list.size shouldEqual 2
    list.map(status => status.getPath.getName) should contain allOf("t1.txt", "t2.txt")
  }
}
