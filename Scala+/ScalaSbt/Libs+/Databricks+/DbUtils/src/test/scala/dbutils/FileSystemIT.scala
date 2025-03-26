package dbutils

import com.databricks.dbutils_v1.DBUtilsHolder
import org.apache.hadoop.fs.Path
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Needs `org.apache.hadoop:hadoop-client-api`
 * Can work only in Databricks.
 */
class FileSystemIT extends AnyFlatSpec with Matchers {
  private val dbfsTmpDir = "dbfs:/tmp"
  private val dbutils = DBUtilsHolder.dbutils

  it should "write a DBFS file" in {
    val path: Path = new Path(dbfsTmpDir + "/file1.txt")
    val dos = dbutils.fs.dbfs.create(path)
    dos.writeBytes("Hello, World!")
    dos.close()
  }

  it should "check if a DBFS file exists (absent)" in {
    val path: Path = new Path(dbfsTmpDir + "/absent_file.txt")
    val exists = dbutils.fs.dbfs.exists(path)
    println(exists)
    exists shouldBe false
  }

  it should "check if a DBFS file exists (absent)" in {
    val path: Path = new Path(dbfsTmpDir)
    val exists = dbutils.fs.dbfs.exists(path)
    println(exists)
    exists shouldBe true
  }

}
