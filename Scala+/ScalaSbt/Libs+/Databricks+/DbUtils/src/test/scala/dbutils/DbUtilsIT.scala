package dbutils

import com.databricks.dbutils_v1.DBUtilsHolder
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * Can work only in Databricks.
 */
class DbUtilsIT extends AnyFlatSpec with Matchers {
  private val dbfsTmpDir = "dbfs:/tmp"
  private val dbutils = DBUtilsHolder.dbutils

  it should "make a directory" in {
    val created = dbutils.fs.mkdirs(dbfsTmpDir + "/dir1/dir2")
    created shouldBe true
  }

  it should "list files in a directory" in {
    val files = dbutils.fs.ls(dbfsTmpDir)
    files.foreach(file => println(file.path))
    files shouldNot be(empty)
  }

}
