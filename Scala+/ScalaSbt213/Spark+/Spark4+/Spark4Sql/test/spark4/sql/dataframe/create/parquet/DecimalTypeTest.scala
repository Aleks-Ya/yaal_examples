package spark4.sql.dataframe.create.parquet

import org.apache.spark.sql.Row
import org.scalatest.flatspec.AnyFlatSpec
import spark4.sql.{Factory, SparkMatchers}
import util.FileUtil

class DecimalTypeTest extends AnyFlatSpec with SparkMatchers {
  private final val file = FileUtil.createAbsentTmpDirStr()

  it should "write and read decimal column" in {
    Factory.createDf("name STRING, num1 DECIMAL(5, 2), num2 DECIMAL(4, 1), num3 DECIMAL(38, 18)",
      Row("John", BigDecimal("123.45"), BigDecimal("67.8"), BigDecimal("999.99"))
    ).write.parquet(file)

    val df = Factory.ss.read.parquet(file)
    df shouldHaveDDL "name STRING,num1 DECIMAL(5,2),num2 DECIMAL(4,1),num3 DECIMAL(38,18)"
    df shouldContain """{"name":"John","num1":123.45,"num2":67.8,"num3":999.990000000000000000}"""
  }

  it should "exceed the max decimal precision" in {
    the[ArithmeticException] thrownBy {
      Factory.createDf("num1 DECIMAL(39, 18)",
        Row(BigDecimal("123.45"))
      ).write.parquet(file)
    } should have message "[DECIMAL_PRECISION_EXCEEDS_MAX_PRECISION] Decimal precision 39 exceeds max precision 38."
  }

}
