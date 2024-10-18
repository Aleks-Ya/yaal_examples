package dataset.transformation.persist

import factory.{City, Factory}
import org.apache.spark.sql.{Encoder, Encoders}
import org.apache.spark.storage.StorageLevel.{DISK_ONLY, MEMORY_AND_DISK, NONE}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PersistTransformation extends AnyFlatSpec with Matchers {
  it should "persist and unpersist a Dataset" in {
    val ds = Factory.cityDs
    ds.storageLevel shouldBe NONE

    val persistedDs = ds.persist()
    ds.storageLevel shouldBe MEMORY_AND_DISK
    persistedDs.storageLevel shouldBe MEMORY_AND_DISK

    persistedDs.unpersist()
    ds.storageLevel shouldBe NONE
    persistedDs.storageLevel shouldBe NONE
  }

  it should "unpersist ancestors" in {
    implicit val encoder: Encoder[City] = Encoders.product[City]

    val ds1 = Factory.cityDs
    ds1.storageLevel shouldBe NONE

    val ds1Persisted = ds1.persist()
    ds1.storageLevel shouldBe MEMORY_AND_DISK
    ds1Persisted.storageLevel shouldBe MEMORY_AND_DISK

    val ds2 = ds1Persisted.map(city => City(city.name.toUpperCase(), city.establishYear * 2))
    ds1.storageLevel shouldBe MEMORY_AND_DISK
    ds1Persisted.storageLevel shouldBe MEMORY_AND_DISK
    ds2.storageLevel shouldBe NONE

    val ds2Persisted = ds2.persist()
    ds1.storageLevel shouldBe MEMORY_AND_DISK
    ds1Persisted.storageLevel shouldBe MEMORY_AND_DISK
    ds2.storageLevel shouldBe MEMORY_AND_DISK
    ds2Persisted.storageLevel shouldBe MEMORY_AND_DISK

    val ds3 = ds1Persisted.map(city => City(city.name.toLowerCase(), city.establishYear * 3))
    ds1.storageLevel shouldBe MEMORY_AND_DISK
    ds1Persisted.storageLevel shouldBe MEMORY_AND_DISK
    ds2.storageLevel shouldBe MEMORY_AND_DISK
    ds2Persisted.storageLevel shouldBe MEMORY_AND_DISK
    ds3.storageLevel shouldBe NONE

    val ds3Persisted = ds3.persist(DISK_ONLY)
    ds1.storageLevel shouldBe MEMORY_AND_DISK
    ds1Persisted.storageLevel shouldBe MEMORY_AND_DISK
    ds2.storageLevel shouldBe MEMORY_AND_DISK
    ds2Persisted.storageLevel shouldBe MEMORY_AND_DISK
    ds3.storageLevel shouldBe DISK_ONLY
    ds3Persisted.storageLevel shouldBe DISK_ONLY

    ds3Persisted.unpersist()
    ds1.storageLevel shouldBe MEMORY_AND_DISK
    ds1Persisted.storageLevel shouldBe MEMORY_AND_DISK
    ds2.storageLevel shouldBe MEMORY_AND_DISK
    ds2Persisted.storageLevel shouldBe MEMORY_AND_DISK
    ds3.storageLevel shouldBe NONE
    ds3Persisted.storageLevel shouldBe NONE
  }
}
