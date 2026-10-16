package spark4.sql.dataframe.operation.transformation

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{AnalysisException, Row}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}

class ToTransformationTest extends AnyFlatSpec with SparkMatchers {

  it should "reorder columns in a DataFrame" in {
    val df = Factory.createDf("name STRING, year INT",
      Row("John", 2020),
      Row("Mary", 2021)
    )
    val schema2 = StructType.fromDDL("year INT, name STRING")
    val reorderedDf = df.to(schema2)
    reorderedDf.schema shouldEqual schema2
    reorderedDf shouldHaveDDL "year INT,name STRING"
    reorderedDf shouldContain(
      """{"year":2020,"name":"John"}""",
      """{"year":2021,"name":"Mary"}"""
    )
  }

  it should "remove unused columns" in {
    val df = Factory.createDf("name STRING, year INT, employed BOOLEAN",
      Row("John", 2020, true),
      Row("Mary", 2021, false)
    )
    val schema2 = StructType.fromDDL("name STRING, employed BOOLEAN")
    val reorderedDf = df.to(schema2)
    reorderedDf.schema shouldEqual schema2
    reorderedDf shouldHaveDDL "name STRING,employed BOOLEAN"
    reorderedDf shouldContain(
      """{"name":"John","employed":true}""",
      """{"name":"Mary","employed":false}"""
    )
  }

  it should "cast columns" in {
    val df = Factory.createDf("name STRING, year INT, price DOUBLE",
      Row("John", 2020, 100.0),
      Row("Mary", 2021, 200.0)
    )
    val schema2 = StructType.fromDDL("name STRING, year STRING, price INT")
    val reorderedDf = df.to(schema2)
    reorderedDf.schema shouldEqual schema2
    reorderedDf shouldHaveDDL "name STRING,year STRING,price INT"
    reorderedDf shouldContain(
      """{"name":"John","year":"2020","price":100}""",
      """{"name":"Mary","year":"2021","price":200}"""
    )
  }

  it should "succeed on compatible nullable" in {
    val df = Factory.createDf("name STRING NOT NULL",
      Row("John"),
      Row("Mary")
    )
    val schema2 = StructType.fromDDL("name STRING")
    val reorderedDf = df.to(schema2)
    reorderedDf shouldHaveDDL "name STRING NOT NULL"
    reorderedDf shouldContain(
      """{"name":"John"}""",
      """{"name":"Mary"}"""
    )
  }

  it should "fail on incompatible nullable" in {
    val df = Factory.createDf("name STRING",
      Row("John")
    )
    val schema2 = StructType.fromDDL("name STRING NOT NULL")
    val e = the[AnalysisException] thrownBy {
      df.to(schema2)
    }
    e.getMessage() should include("""[NULLABLE_COLUMN_OR_FIELD] Column or field `name` is nullable while it's required to be non-nullable.""")
  }

}