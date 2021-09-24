package hdfs.local

import hdfs.local.Factory.fs
import org.apache.hadoop.fs.{FSDataInputStream, FSDataOutputStream, Path}
import org.apache.hadoop.util.Progressable
import org.scalatest._

class ProgressableTest extends FlatSpec with Matchers {

  it should "read file twice using random access" in {
    val file: Path = new Path("/tmp", "ProgressableTest.txt")
    var os: FSDataOutputStream = null
    try {
      os = fs.create(file, new Progressable() {
        override def progress(): Unit = println("progress! ") //TODO don't print
      })
      println("Start writing")
      for (i <- 0 to 100000000) os.write(i)
      os.close()
    } finally {
      if (fs.exists(file)) {
        println("file deleted: " + fs.delete(file, false))
      }
    }
  }
}
