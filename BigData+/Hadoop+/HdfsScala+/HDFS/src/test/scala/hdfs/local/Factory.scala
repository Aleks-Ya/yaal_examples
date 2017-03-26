package hdfs.local

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FSDataOutputStream, FileSystem, LocalFileSystem, Path}

object Factory {
  val tmpDir = "/tmp"
  lazy val conf = new Configuration()
  lazy val fs: LocalFileSystem = FileSystem.getLocal(conf)

  def writeTmpFile(name: String, content: String): Path = {
    val file = new Path(tmpDir, name)
    val os: FSDataOutputStream = fs.create(file)
    os.writeUTF(content)
    os.close()
    file
  }
}
