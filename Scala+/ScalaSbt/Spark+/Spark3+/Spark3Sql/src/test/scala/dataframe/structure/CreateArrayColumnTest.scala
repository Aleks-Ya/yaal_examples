package dataframe.structure

import factory.Factory
import org.apache.spark.sql.Row
import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CreateArrayColumnTest extends AnyFlatSpec with Matchers {

  it should "create array StructureType" in {
    val schema = StructType(StructField("name", ArrayType(IntegerType)) :: Nil)
    val rdd = Factory.ss.sparkContext.parallelize(Seq(Array(1, 2, 3), Array(4, 5, 6))).map(arr => Row(arr))
    val df = Factory.ss.sqlContext.createDataFrame(rdd, schema)
    df.show
    df.printSchema
  }
}