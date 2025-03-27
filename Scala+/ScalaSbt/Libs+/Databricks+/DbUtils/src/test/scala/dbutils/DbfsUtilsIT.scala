package dbutils

import com.databricks.dbutils_v1.{DBUtilsHolder, DbfsUtils}
import org.scalatest.flatspec.AnyFlatSpec

/**
 * Can work only in Databricks.
 */
class DbfsUtilsIT extends AnyFlatSpec {
  private val dbfsTmpDir = "dbfs:/tmp"
  private val fs: DbfsUtils = DBUtilsHolder.dbutils.fs

  it should "make a directory" in {
    val created = fs.mkdirs(dbfsTmpDir + "/dir1/dir2")
    assert(created)
  }

  it should "list files in a directory" in {
    val files = fs.ls(dbfsTmpDir)
    files.foreach(file => println(file.path))
    assert(files.nonEmpty)
  }

}
