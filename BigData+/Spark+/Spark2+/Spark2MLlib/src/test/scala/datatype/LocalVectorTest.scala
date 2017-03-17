package datatype

import org.apache.spark.ml.linalg.Vectors
import org.scalatest.{FlatSpec, Matchers}

class LocalVectorTest extends FlatSpec with Matchers {

  it should "init a dense vector" in {
    val dv = Vectors.dense(1.0, 0.0, 3.0)
    dv.toString shouldEqual "[1.0,0.0,3.0]"
  }

  it should "init a sparse vector" in {
    // Create a sparse vector (1.0, 0.0, 3.0) by specifying its indices and values corresponding to nonzero entries.
    val sv1 = Vectors.sparse(3, Array(0, 2), Array(1.0, 3.0))

    // Create a sparse vector (1.0, 0.0, 3.0) by specifying its nonzero entries.
    val sv2 = Vectors.sparse(3, Seq((0, 1.0), (2, 3.0)))

    val expected = "(3,[0,2],[1.0,3.0])"
    sv1.toString shouldEqual expected
    sv2.toString shouldEqual expected
    sv1 shouldEqual sv2
  }

}
