package dataframe.create

import factory.Factory
import org.scalatest.FlatSpec

class JdbcTest extends FlatSpec {

  it should "Read Df from a database via JDBC" in {
    val initSqlScript = classOf[JdbcTest].getResource("JdbcTest.sql").getFile
    val jdbcUrl = s"jdbc:h2:mem:;INIT=RUNSCRIPT FROM '$initSqlScript'"
    val tableName = "people"
    val df = Factory.ss.read.format("jdbc")
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

}