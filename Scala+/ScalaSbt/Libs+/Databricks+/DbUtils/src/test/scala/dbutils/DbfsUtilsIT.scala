package dbutils

import com.databricks.dbutils_v1.{DBUtilsHolder, DbfsUtils}
import org.scalatest.flatspec.AnyFlatSpec

import java.io.FileNotFoundException

/**
 * Can work only in Databricks.
 */
class DbfsUtilsIT extends AnyFlatSpec {
  private val dbfsTmpDir = "dbfs:/tmp"
  private val fs: DbfsUtils = DBUtilsHolder.dbutils.fs

  private def absentDbfsDir = s"$dbfsTmpDir/${java.util.UUID.randomUUID()}"

  private def absentDbfsFile = s"$absentDbfsDir/file.tmp"

  private def createDbfsFile(dbfsFile: String, content: String = ""): String = {
    assert(fs.put(dbfsFile, content))
    dbfsFile
  }

  it should "create a DBFS directory" in {
    val dir = absentDbfsDir + "/dir1/dir2"
    val created = fs.mkdirs(dir)
    assert(created)
  }

  it should "delete a DBFS directory" in {
    val emptyDbfsDir = absentDbfsDir
    assert(fs.mkdirs(emptyDbfsDir))
    val deleted = fs.rm(emptyDbfsDir, recurse = true)
    assert(deleted)
  }

  it should "list files in a directory" in {
    val files = fs.ls(dbfsTmpDir)
    files.foreach(file => println(file.path))
    assert(files.nonEmpty)
  }

  it should "write a String to a DBFS file" in {
    val dbfsFile = absentDbfsFile
    val expContent = "abc"
    val success = fs.put(dbfsFile, expContent)
    assert(success)
    val actContent = fs.head(dbfsFile)
    assert(actContent == expContent)
  }

  it should "is a DBFS file exist" in {
    import java.io.FileNotFoundException
    def isFileExist(dbfsFile: String): Boolean = {
      try {
        fs.ls(dbfsFile)
        true
      } catch {
        case _: FileNotFoundException => false
        case e: Exception => throw e
      }
    }

    val existsFile = createDbfsFile(absentDbfsFile)
    val absentFile = absentDbfsFile
    assert(isFileExist(existsFile))
    assert(!isFileExist(absentFile))
  }

  it should "get size of a DBFS file" in {
    def getDbfsFileSize(dbfsFile: String): Long = {
      try {
        fs.ls(dbfsFile).head.size
      } catch {
        case _: FileNotFoundException => 0
        case e: Exception => throw e
      }
    }

    val content = "abc"
    val existsFile = createDbfsFile(absentDbfsFile, content)
    val absentFile = absentDbfsFile
    val existingFileSize = getDbfsFileSize(existsFile)
    val absentFileSize = getDbfsFileSize(absentFile)
    assert(existingFileSize == content.length)
    assert(absentFileSize == 0)
  }

  it should "copy a DBFS file to a local file" in {
    import java.nio.file.Files
    val localFile = Files.createTempFile("local", ".txt")
    Files.delete(localFile)
    assert(Files.notExists(localFile))

    val expContent = "abc"
    val dbfsFile = absentDbfsFile
    assert(fs.put(dbfsFile, expContent))

    val localFileUri = localFile.toUri.toString
    val success = fs.cp(dbfsFile, localFileUri, recurse = true)
    assert(success)
    val actContent = Files.readString(localFile)
    assert(actContent == expContent)
  }

  it should "copy a local file to a DBFS file" in {
    import java.nio.file.Files
    val expContent = "abc"
    val localFile = Files.createTempFile("local", ".txt")
    Files.writeString(localFile, expContent)
    val localFileUri = localFile.toUri.toString
    val dbfsFile = absentDbfsFile
    val success = fs.cp(localFileUri, dbfsFile, recurse = true)
    assert(success)
    val actContent = fs.head(dbfsFile)
    assert(actContent == expContent)
  }

  /**
   * NOT WORK: `Unsupported scheme: dbfs. Allowed schemes are: gs,s3a,s3n,wasbs,adl,abfss.`
   */
  it should "mount a DBFS dir to a local dir" in {
    val dbfsDir = dbfsTmpDir + "/dir1"
    assert(fs.mkdirs(dbfsDir))
    val mountPoint = "/mnt/dbfs"
    val localFile = mountPoint + "/file.txt"
    assert(fs.mount(dbfsTmpDir, mountPoint))
    assert(fs.put(localFile, "abc"))
    assert(fs.unmount(mountPoint))
  }

}
