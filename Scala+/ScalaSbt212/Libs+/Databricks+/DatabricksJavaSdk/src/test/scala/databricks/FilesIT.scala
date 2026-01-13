package databricks

import com.databricks.sdk.WorkspaceClient
import com.databricks.sdk.service.files.FilesAPI
import org.scalatest.flatspec.AnyFlatSpec

import java.io.{ByteArrayInputStream, File, FileOutputStream}
import java.nio.file.Files
import java.util.UUID

/**
 * NOT TESTED in Databricks
 */
class FilesIT extends AnyFlatSpec {
  private val w = new WorkspaceClient
  private val files: FilesAPI = w.files()
  private val dbfsTmpDir = "dbfs:/tmp/"

  private def absentDbfsFile = s"$dbfsTmpDir/${UUID.randomUUID()}.tmp"

  it should "list files in a DBFS dir" in {
    val entries = files.listDirectoryContents("/")
    entries.forEach(println)
  }

  it should "upload a DBFS file" in {
    val path = absentDbfsFile
    val content = "Hello World 1!".getBytes
//    files.upload(path, new ByteArrayInputStream(content))
  }

  it should "read a DBFS file as an InputStream" in {
    val response = files.download(absentDbfsFile)
    val bytes = response.getContents.readAllBytes
    val content = new String(bytes)
    println(content)
  }

  it should "get metadata of a DBFS file" in {
    val response = files.getMetadata(absentDbfsFile)
    println(response)
    val size = response.getContentLength
    println(size)
  }

  it should "delete a DBFS file" in {
    files.delete(absentDbfsFile)
  }

  it should "create a DBFS dir" in {
//    files.createDirectory("dbfs:/tmp/iabloal1/d1/d2")
  }

  it should "delete a DBFS dir" in {
    files.deleteDirectory("dbfs:/tmp/iabloal1/d1/d2")
  }

  it should "copy a DBFS file to a local file" in {
    val dbfsPath = absentDbfsFile
    val expContent = "Hello World 1!"
//    files.upload(dbfsPath, new ByteArrayInputStream(expContent.getBytes))
    val localPath = File.createTempFile("dbfs_", ".tmp")
    localPath.delete()
    assert(!localPath.exists())
    val response = files.download(dbfsPath)
    val is = response.getContents
    val os = new FileOutputStream(localPath)
    is.transferTo(os)
    os.close()
    assert(Files.readString(localPath.toPath) == expContent)
  }

}