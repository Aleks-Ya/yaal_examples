package datatype

import org.apache.spark.ml.feature.LabeledPoint
import org.apache.spark.ml.linalg.Vectors
import org.scalatest.{FlatSpec, Matchers}

class LabeledPointTest extends FlatSpec with Matchers {

  it should "init a dense vector" in {
    val vector = Vectors.dense(1.0, 0.0, 3.0)
    val positiveLabel = 1.0
    val pl = LabeledPoint(positiveLabel, vector)
    pl.toString shouldEqual "(1.0,[1.0,0.0,3.0])"
  }

  it should "init a sparse vector" in {
    val vector = Vectors.sparse(3, Array(0, 2), Array(1.0, 3.0))
    val positiveLabel = 1.0
    val pl = LabeledPoint(positiveLabel, vector)
    pl.toString shouldEqual "(1.0,(3,[0,2],[1.0,3.0]))"
  }

}
