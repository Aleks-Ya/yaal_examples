package dataframe.write

import java.io.File
import java.util.Properties

import factory.Factory
import org.apache.spark.sql.SaveMode
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class WriteJdbc extends FlatSpec {

  it should "write DataFrame to JDBC database" in {
    val h2TmpDir = File.createTempFile(getClass.getSimpleName, ".tmp")
    h2TmpDir.deleteOnExit()
    val jdbcUrl = s"jdbc:h2:${h2TmpDir.getAbsolutePath}"
    val tableName = "people"

    val properties = new Properties()
    properties.setProperty("driver", "org.h2.Driver")

    val expDf = Factory.peopleDf

    expDf
      .write
      .mode(SaveMode.Overwrite)
      .jdbc(jdbcUrl, tableName, properties)

    val actDf = Factory.ss
      .read
      .jdbc(jdbcUrl, tableName, properties)

    actDf.schema shouldEqual expDf.schema
    actDf.collect() shouldEqual expDf.collect()
  }

}