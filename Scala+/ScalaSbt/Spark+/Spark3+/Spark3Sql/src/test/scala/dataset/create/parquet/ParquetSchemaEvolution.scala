package dataset.create.parquet

import factory.Factory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.Files

class ParquetSchemaEvolution extends AnyFlatSpec with Matchers with BeforeAndAfterAll {

  "Parquet test" should "write a parquet file" in {
    val ss = Factory.ss
    import org.apache.spark.sql._
    import ss.implicits._

    val data = Seq(Person("John", 30, gender = true, 0.95D), Person("Mary", 25, gender = false, 0.90D))
    val schema = Encoders.product[Person].schema
    val expDs = ss.createDataset(data)
    val file = Files.createTempFile(classOf[ParquetSchemaEvolution].getSimpleName, ".parquet")
    Files.delete(file)
    expDs.write.parquet(file.toString)

    val actDs = ss.read.schema(schema).parquet(file.toString).as[Person]

    expDs.show(false)
    actDs.show(false)
    val strings = expDs.collect.map(person => person.name + "-" + person.age.toString + "-" + person.gender + "-" + person.rating)
    strings should contain allOf("John-30-true-0.95", "Mary-25-false-0.9")
  }

}

case class Person(name: String, age: Integer, gender: Boolean, rating: Double)