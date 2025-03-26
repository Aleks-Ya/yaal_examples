package dbutils

import com.databricks.dbutils_v1.DBUtilsHolder
import org.apache.hadoop.fs.{FileSystem, Path}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Can work only in Databricks.
 */
class FsTest extends AnyFlatSpec with Matchers {

  it should "make a directory" in {
    val created = DBUtilsHolder.dbutils.fs.mkdirs("/tmp/dir1/dir2")
    created shouldBe true
  }

  /**
   * Needs hadoop-client-api
   */
  it should "make a directory" in {
    val dbfs: FileSystem = DBUtilsHolder.dbutils.fs.dbfs
    val path: Path = new Path("/tmp/file1.txt")
    val dos = dbfs.create(path)
    dos.writeBytes("Hello, World!")
    dos.close()
  }

}
