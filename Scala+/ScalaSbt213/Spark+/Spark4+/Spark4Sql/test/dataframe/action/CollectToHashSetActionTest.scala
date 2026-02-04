package dataframe.action

import factory.Factory
import org.apache.spark.sql.{Encoder, Encoders, Row}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable

class CollectToHashSetActionTest extends AnyFlatSpec with Matchers {
  it should "collect a String DataFrame to a HashSet" in {
    val df = Factory.createDf("city STRING", Row("London"), Row("Berlin"), Row("Paris"))
    implicit val encoder: Encoder[String] = Encoders.STRING

    val citiesArray: Array[String] = df.as[String].collect()
    citiesArray should contain allOf("London", "Berlin", "Paris")

    val citiesSet: mutable.Set[String] = df.as[String].collect()
      .foldLeft(mutable.HashSet.empty[String])((set, item) => set += item)
    citiesSet should contain allOf("London", "Berlin", "Paris")
  }
}