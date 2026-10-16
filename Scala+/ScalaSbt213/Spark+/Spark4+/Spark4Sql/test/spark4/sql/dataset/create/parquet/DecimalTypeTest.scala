package spark4.sql.dataset.create.parquet

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Dataset, Encoders, Row}
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}
import util.FileUtil

class DecimalTypeTest extends AnyFlatSpec with SparkMatchers {
  private val ss = Factory.ss

  import ss.implicits._

  it should "write and read a decimal column" in {
    val file = FileUtil.createAbsentTmpFile(".parquet").toString

    val expSeq: Seq[DecimalTypesData] = Seq(
      DecimalTypesData("John", BigDecimal("123.45")),
      DecimalTypesData("Mary", BigDecimal("67.8"))
    )
    val expDs: Dataset[DecimalTypesData] = ss.createDataset(expSeq)
    expDs.write.parquet(file)

    val schema: StructType = Encoders.product[DecimalTypesData].schema
    val actDs: Dataset[DecimalTypesData] = ss.read.schema(schema).parquet(file).as[DecimalTypesData]
    actDs shouldContain (expSeq: _*)
    actDs shouldHaveDDL "name STRING,num1 DECIMAL(38,18)"
  }

  it should "read a decimal column having 38-18 precision" in {
    val file = FileUtil.createAbsentTmpFile(".parquet").toString

    Factory.createDf("name STRING, num1 DECIMAL(38,18)",
      Row("John", BigDecimal("123.45")),
      Row("Mary", BigDecimal("678.90"))
    ).write.parquet(file)

    val schema: StructType = Encoders.product[DecimalTypesData].schema
    val actDs: Dataset[DecimalTypesData] = ss.read.schema(schema).parquet(file).as[DecimalTypesData]
    actDs shouldContain(
      DecimalTypesData("John", BigDecimal("123.45")),
      DecimalTypesData("Mary", BigDecimal("678.90"))
    )
    actDs shouldHaveDDL "name STRING,num1 DECIMAL(38,18)"
  }

  it should "read a decimal column having lower precision" in {
    val file = FileUtil.createAbsentTmpFile(".parquet").toString

    Factory.createDf("name STRING, num1 DECIMAL(37,17)",
      Row("John", BigDecimal("123.45")),
      Row("Mary", BigDecimal("678.90"))
    ).write.parquet(file)

    val actDs: Dataset[DecimalTypesData] = ss.read.parquet(file).as[DecimalTypesData]
    actDs shouldContain(
      DecimalTypesData("John", BigDecimal("123.45")),
      DecimalTypesData("Mary", BigDecimal("678.90"))
    )
    actDs shouldHaveDDL "name STRING,num1 DECIMAL(37,17)"
  }

}
