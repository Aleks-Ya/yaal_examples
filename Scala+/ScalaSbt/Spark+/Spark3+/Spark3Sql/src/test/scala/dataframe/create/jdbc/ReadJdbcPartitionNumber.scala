package dataframe.create.jdbc

import factory.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.Properties

/**
 * Read JDBC source in several partitions.
 */
class ReadJdbcPartitionNumber extends AnyFlatSpec with Matchers {

  private val jdbcUrl = {
    val initSqlScript = classOf[ReadJdbcPartitionNumber].getResource("JdbcTest.sql").getFile
    s"jdbc:h2:mem:;INIT=RUNSCRIPT FROM '$initSqlScript'"
  }
  private val tableName = "people"
  private val partitionNumber = 2
  private val jdbcDriver = "org.h2.Driver"
  private val partitionColumn = "id"
  private val lowerBound = Long.MinValue
  private val upperBound = Long.MaxValue

  it should "Read DF from JDBC by load()" in {
    val df = Factory.ss
      .read
      .format("jdbc")
      .option("url", jdbcUrl)
      .option("driver", jdbcDriver)
      .option("dbtable", tableName)
      .option("partitionColumn", partitionColumn)
      .option("lowerBound", lowerBound)
      .option("upperBound", upperBound)
      .option("numPartitions", partitionNumber)
      .load()

    df.rdd.getNumPartitions shouldEqual partitionNumber
  }

  it should "Read DF from JDBC by jdbc()" in {
    val properties = new Properties()
    properties.setProperty("driver", jdbcDriver)

    val df = Factory.ss
      .read
      .jdbc(
        url = jdbcUrl,
        table = tableName,
        lowerBound = lowerBound,
        upperBound = upperBound,
        numPartitions = partitionNumber,
        columnName = partitionColumn,
        connectionProperties = properties
      )

    df.rdd.getNumPartitions shouldEqual partitionNumber
  }

}