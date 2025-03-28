package databricks

import com.databricks.sdk.WorkspaceClient
import com.databricks.sdk.mixin.DbfsExt
import org.scalatest.flatspec.AnyFlatSpec

import java.net.URI
import java.nio.file.Path

class DbfsIT extends AnyFlatSpec {
  private val w = new WorkspaceClient
  private val dbfs: DbfsExt = w.dbfs
  private val dbfsTmpDir = "dbfs:/tmp/"

  private def absentDbfsFile = s"$dbfsTmpDir/${java.util.UUID.randomUUID()}.tmp"

  private def createFile(path: String, content: String = ""): String = {
    val os = dbfs.getOutputStream(path)
    os.write(content.getBytes)
    os.close()
    path
  }

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

  it should "get size of a DBFS file" in {
    val expContent = "Hello World 1!"
    val file = createFile(absentDbfsFile, expContent)
    val status = dbfs.getStatus(file)
    val size = status.getFileSize
    assert(size == expContent.length)
  }

  it should "chick is a DBFS file exist" in {
    import com.databricks.sdk.core.error.platform.ResourceDoesNotExist
    def isExists(path: String): Boolean = {
      try {
        dbfs.getStatus(path)
        true
      } catch {
        case _: ResourceDoesNotExist => false
        case e: Exception => throw e
      }
    }

    val existingFile = createFile(absentDbfsFile)
    assert(isExists(existingFile))
    val absentFile = absentDbfsFile
    assert(!isExists(absentFile))
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
    import java.io.FileOutputStream
    import java.nio.file.Files
    val expContent = "Hello World 1!"
    val dbfsPath = createFile(absentDbfsFile, expContent)
    val localPath = Files.createTempFile("dbfs_", ".tmp")
    Files.delete(localPath)
    assert(Files.notExists(localPath))
    val is = dbfs.open(dbfsPath)
    val os = new FileOutputStream(localPath.toFile)
    is.transferTo(os)
    is.close()
    os.close()
    assert(Files.readString(localPath) == expContent)
  }

  it should "write bytes to a DBFS file" in {
    val uri = URI.create(absentDbfsFile)
    val path = Path.of(uri)
    val content = "Hello World 1!".getBytes
    val actPath = dbfs.write(path, content)
    println(actPath)
  }

}