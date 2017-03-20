package datatype.vector

import factory.Factory
import org.apache.spark.ml.linalg.{Vector, VectorUDTPublic, Vectors}
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StructField, StructType}
import org.scalatest.{FlatSpec, Matchers}

/**
  * Save exists Vector to DataFrame column.
  */
class VectorToDataFrameTest extends FlatSpec with Matchers {

  it should "init a dense vector" in {
    val ss = Factory.ss
    val dv = Vectors.dense(1.0, 0.0, 3.0)
    val rdd = ss.sparkContext.makeRDD(Seq(dv)).map(vector => Row(vector))

    val schema = StructType(
      StructField("vector", VectorUDTPublic, nullable = false) :: Nil
    )

    val df = ss.createDataFrame(rdd, schema)
    df.show
    df.printSchema

    val vectors = df.collect().map(row => row.getAs[Vector](0))
    vectors should contain(dv)
  }

}
