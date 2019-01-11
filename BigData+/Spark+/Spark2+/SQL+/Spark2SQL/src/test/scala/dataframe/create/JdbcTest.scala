package dataframe.create

import java.util.Properties

import factory.Factory
import org.scalatest.FlatSpec

class JdbcTest extends FlatSpec {

  it should "Read DF from via JDBC  (approach 1)" in {
    val initSqlScript = classOf[JdbcTest].getResource("JdbcTest.sql").getFile
    val jdbcUrl = s"jdbc:h2:mem:;INIT=RUNSCRIPT FROM '$initSqlScript'"
    val tableName = "people"
    val df = Factory.ss
      .read
      .format("jdbc")
      .option("url", jdbcUrl)
      .option("driver", "org.h2.Driver")
      .option("dbtable", tableName)
      //      .option("user", "root")
      //      .option("password", "root")
      .load()
    df.createOrReplaceTempView(tableName)
    df.sqlContext.sql(s"select * from $tableName").collect.foreach(println)

    df.printSchema()
    df.show()
  }

  it should "Read DF from via JDBC (approach 2)" in {
    val initSqlScript = classOf[JdbcTest].getResource("JdbcTest.sql").getFile
    val jdbcUrl = s"jdbc:h2:mem:;INIT=RUNSCRIPT FROM '$initSqlScript'"
    val tableName = "people"

    val properties = new Properties()
    properties.setProperty("driver", "org.h2.Driver")
    //properties.setProperty("user", "my_user")

    val df = Factory.ss
      .read
      .jdbc(jdbcUrl, tableName, properties)

    df.createOrReplaceTempView(tableName)
    df.sqlContext.sql(s"select * from $tableName").collect.foreach(println)

    df.printSchema()
    df.show()
  }

}