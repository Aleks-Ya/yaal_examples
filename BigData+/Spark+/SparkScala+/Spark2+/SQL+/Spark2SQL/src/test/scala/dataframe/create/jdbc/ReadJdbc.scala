package dataframe.create.jdbc

import java.util.Properties

import factory.Factory
import org.scalatest.{FlatSpec, Matchers}

/**
  * Read JDBC source in single partition.
  */
class ReadJdbc extends FlatSpec with Matchers {

  private val jdbcUrl = {
    val initSqlScript = classOf[ReadJdbcPartitionNumber].getResource("JdbcTest.sql").getFile
    s"jdbc:h2:mem:;INIT=RUNSCRIPT FROM '$initSqlScript'"
  }
  private val tableName = "people"
  private val jdbcDriver = "org.h2.Driver"

  it should "Read DF from JDBC by load()" in {
    val df = Factory.ss
      .read
      .format("jdbc")
      .option("url", jdbcUrl)
      .option("driver", jdbcDriver)
      .option("dbtable", tableName)
      .load()

    df.rdd.collect().length shouldEqual 2

    df.createOrReplaceTempView(tableName)
    df.sqlContext.sql(s"select * from $tableName").collect.foreach(println)

    df.printSchema()
    df.show()
  }

  it should "Read DF from JDBC by jdbc()" in {
    val properties = new Properties()
    properties.setProperty("driver", jdbcDriver)

    val df = Factory.ss
      .read
      .jdbc(jdbcUrl, tableName, properties)

    df.rdd.collect().length shouldEqual 2

    df.createOrReplaceTempView(tableName)
    df.sqlContext.sql(s"select * from $tableName").collect.foreach(println)

    df.printSchema()
    df.show()
  }

}