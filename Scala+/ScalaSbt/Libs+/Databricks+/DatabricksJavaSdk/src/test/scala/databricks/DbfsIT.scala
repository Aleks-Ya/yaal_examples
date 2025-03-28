package databricks

import com.databricks.sdk.WorkspaceClient
import com.databricks.sdk.mixin.DbfsExt
import org.apache.commons.io.IOUtils
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.{File, FileOutputStream}
import java.net.URI
import java.nio.file.{Files, Path}
import java.util.UUID

class DbfsIT extends AnyFlatSpec with Matchers {
  private val w = new WorkspaceClient
  private val dbfs: DbfsExt = w.dbfs
  private val dbfsTmpDir = "dbfs:/tmp/"

  private def absentDbfsFile = s"$dbfsTmpDir/${UUID.randomUUID()}.tmp"

  it should "list files in a DBFS dir" in {
    val files = dbfs.list("/")
    files.forEach(println)
  }

  it should "list files in a DBFS dir recursively" in {
    val files = dbfs.recursiveList("/tmp")
    files.forEach(println)
  }

  it should "write a DBFS file as an OutputStream" in {
    val path = absentDbfsFile
    val content = "Hello World 1!".getBytes
    val os = dbfs.getOutputStream(path)
    os.write(content)
    os.close()
  }

  it should "read a DBFS file as an InputStream" in {
    val is = dbfs.open(absentDbfsFile)
    val bytes = is.readAllBytes
    val content = new String(bytes)
    println(content)
    is.close()
  }

  it should "get status of a DBFS file" in {
    val status = dbfs.getStatus(absentDbfsFile)
    println(status)
  }

  it should "delete a DBFS file" in {
    dbfs.delete(absentDbfsFile)
  }

  it should "create a DBFS dir" in {
    dbfs.mkdirs("dbfs:/tmp/iabloal1/d1/d2")
  }

  it should "delete a DBFS dir" in {
    dbfs.delete("dbfs:/tmp/iabloal1/d1/d2")
  }

  it should "copy a DBFS file to a local file" in {
    val dbfsPath = absentDbfsFile
    val expContent = "Hello World 1!"
    val osExp = dbfs.getOutputStream(dbfsPath)
    osExp.write(expContent.getBytes)
    osExp.close()
    val localPath = File.createTempFile("dbfs_", ".tmp")
    localPath.delete()
    localPath shouldNot exist
    val is = dbfs.open(dbfsPath)
    val os = new FileOutputStream(localPath)
    IOUtils.copy(is, os)
    is.close()
    os.close()
    Files.readString(localPath.toPath) shouldEqual expContent
  }

  it should "write bytes to a DBFS file" in {
    val uri = URI.create(absentDbfsFile)
    val path = Path.of(uri)
    val content = "Hello World 1!".getBytes
    val actPath = dbfs.write(path, content)
    println(actPath)
  }

}