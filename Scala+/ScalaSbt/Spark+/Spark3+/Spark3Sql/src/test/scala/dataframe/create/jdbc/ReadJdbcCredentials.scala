package dataframe.create.jdbc

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.Properties

/**
 * Read JDBC source with login and password.
 */
class ReadJdbcCredentials extends AnyFlatSpec with Matchers {

  private val jdbcUrl = {
    val initSqlScript = classOf[ReadJdbcPartitionNumber].getResource("JdbcTest.sql").getFile
    s"jdbc:h2:mem:;INIT=RUNSCRIPT FROM '$initSqlScript'"
  }
  private val tableName = "people"
  private val jdbcDriver = "org.h2.Driver"
  private val username = "my_user"
  private val password = "my_password"

  it should "Read DF from JDBC by load()" in {
    val df = Factory.ss
      .read
      .format("jdbc")
      .option("url", jdbcUrl)
      .option("driver", jdbcDriver)
      .option("dbtable", tableName)
      .option("user", username)
      .option("password", password)
      .load()
    df.rdd.collect().length shouldEqual 2
  }

  it should "Read DF from JDBC by jdbc()" in {
    val properties = new Properties()
    properties.setProperty("driver", jdbcDriver)
    properties.setProperty("user", username)
    properties.setProperty("password", password)

    val df = Factory.ss
      .read
      .jdbc(jdbcUrl, tableName, properties)

    df.rdd.collect().length shouldEqual 2
  }

}