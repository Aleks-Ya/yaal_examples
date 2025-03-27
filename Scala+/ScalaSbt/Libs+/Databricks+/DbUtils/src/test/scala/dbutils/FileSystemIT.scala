package dbutils

import com.databricks.dbutils_v1.DBUtilsHolder
import org.apache.hadoop.fs.{FileSystem, Path}
import org.scalatest.flatspec.AnyFlatSpec

import java.io.FileNotFoundException
import java.nio.file.Files
import java.util.UUID

/**
 * Needs `org.apache.hadoop:hadoop-client-api`
 * Can work only in Databricks.
 */
class FileSystemIT extends AnyFlatSpec {
  private val dbfs: FileSystem = DBUtilsHolder.dbutils.fs.dbfs
  private val dbfsTmpDir = "dbfs:/tmp"

  private def absentDbfsFile = new Path(dbfsTmpDir + "/" + UUID.randomUUID() + ".tmp")

  it should "create an empty DBFS file" in {
    val created = dbfs.createNewFile(absentDbfsFile)
    assert(created)
  }

  it should "write a DBFS file" in {
    val dos = dbfs.create(absentDbfsFile)
    dos.writeBytes("Hello, World!")
    dos.close()
  }

  it should "check if a DBFS file exists (absent)" in {
    val exists = dbfs.exists(absentDbfsFile)
    assert(!exists)
  }

  it should "check if a DBFS file exists (present)" in {
    val file = absentDbfsFile
    dbfs.createNewFile(file)
    val exists = dbfs.exists(file)
    assert(exists)
  }

  it should "get status of a DBFS file (exists)" in {
    val file = absentDbfsFile
    val dos = dbfs.create(file)
    dos.writeBytes("abc")
    dos.close()
    val fileStatus = dbfs.getFileStatus(file)
    println(fileStatus)
    val fileSize = fileStatus.getLen
    val isFile = fileStatus.isFile
    val isDirectory = fileStatus.isDirectory
    assert(fileSize == 3)
    assert(isFile)
    assert(!isDirectory)
  }

  it should "get status of a DBFS file (absent)" in {
    try {
      dbfs.getFileStatus(absentDbfsFile)
      throw new AssertionError("Expected FileNotFoundException")
    } catch {
      case e: FileNotFoundException => println(e)
    }
  }

  it should "copy a local file to DBFS" in {
    val localFile = Files.createTempFile("local", ".txt")
    Files.write(localFile, "content 1".getBytes)
    val srcPath = new Path(localFile.toString)

    val dstPath = absentDbfsFile
    assert(!dbfs.exists(dstPath))

    dbfs.copyFromLocalFile(srcPath, dstPath)
    assert(dbfs.exists(dstPath))
  }

  it should "copy a DBFS file to local file" in {
    val srcPath = absentDbfsFile
    val dos = dbfs.create(srcPath)
    dos.writeBytes("Hello, World!")
    dos.close()
    assert(dbfs.exists(srcPath))

    val localFile = Files.createTempFile("local", ".txt")
    Files.delete(localFile)
    val dstPath = new Path(localFile.toString)
    assert(Files.notExists(localFile))

    dbfs.copyToLocalFile(srcPath, dstPath)
    assert(Files.exists(localFile))
  }

}
