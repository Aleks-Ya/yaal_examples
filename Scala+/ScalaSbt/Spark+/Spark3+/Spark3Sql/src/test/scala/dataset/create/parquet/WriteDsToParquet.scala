package dataset.create.parquet

import factory.Factory
import org.apache.spark.sql.functions.{col, lit}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.Files

class WriteDsToParquet extends AnyFlatSpec with Matchers with BeforeAndAfterAll {

  "Parquet test" should "write a parquet file" in {
    val ss = Factory.ss
    import org.apache.spark.sql._
    import ss.sqlContext.implicits._

    val data = Seq(Person2("John", 30, gender = true, 0.95D), Person2("Mary", 25, gender = false, 0.90D))
    val schema = Encoders.product[Person2].schema
    val expDs = ss.createDataset(data)
    val file = Files.createTempFile(classOf[WriteDsToParquet].getSimpleName, ".parquet")
    Files.delete(file)
    expDs.write.parquet(file.toString)

    val ds = ss.read.schema(schema).parquet(file.toString).as[Person2]
    expDs.show(false)
    ds.show(false)
    val actDs = ds
      .withColumnsRenamed(Map("name" -> "identity"))
      .drop(col("rating"))
      .withColumn("age", col("age").cast("double"))
      .withColumn("city", lit("London"))

    val expStrings = actDs.collect.map(person => person.name + "-" + person.age.toString + "-" + person.gender + "-" + person.rating)
    expStrings should contain allOf("John-30-true-0.95", "Mary-25-false-0.9")
  }

}

case class Person2(name: String, age: Integer, gender: Boolean, rating: Double)