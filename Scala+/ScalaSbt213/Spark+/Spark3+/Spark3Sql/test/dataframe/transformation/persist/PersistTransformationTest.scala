package dataframe.transformation.persist

import factory.Factory
import org.apache.spark.storage.StorageLevel.{MEMORY_AND_DISK, MEMORY_ONLY, NONE}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers


class PersistTransformationTest extends AnyFlatSpec with Matchers {

  it should "persist and unpersist a DataFrame" in {
    Factory.sc.getPersistentRDDs shouldBe empty
    val df1 = Factory.peopleDf
    df1.storageLevel shouldEqual NONE

    val df2 = df1.persist(MEMORY_ONLY)
    df1.storageLevel shouldEqual MEMORY_ONLY
    df2.storageLevel shouldEqual MEMORY_ONLY
    Factory.sc.getPersistentRDDs shouldBe empty

    val df3 = df2.unpersist()
    df1.storageLevel shouldEqual NONE
    df2.storageLevel shouldEqual NONE
    df3.storageLevel shouldEqual NONE
    Factory.sc.getPersistentRDDs shouldBe empty
  }

  it should "unpersist by clearing a cache" in {
    Factory.sc.getPersistentRDDs shouldBe empty
    val df1 = Factory.peopleDf.persist(MEMORY_ONLY)
    val df2 = df1.persist(MEMORY_AND_DISK)
    val df3 = Factory.cityListDf.persist(MEMORY_ONLY)
    df1.storageLevel shouldEqual MEMORY_ONLY
    df2.storageLevel shouldEqual MEMORY_ONLY
    df3.storageLevel shouldEqual MEMORY_ONLY
    Factory.sc.getPersistentRDDs shouldBe empty

    Factory.ss.catalog.clearCache()
    df1.storageLevel shouldEqual NONE
    df2.storageLevel shouldEqual NONE
    df3.storageLevel shouldEqual NONE
    Factory.sc.getPersistentRDDs shouldBe empty
  }

}