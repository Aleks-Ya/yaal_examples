package dataframe.create.schema

import factory.Factory
import org.apache.spark.sql.types._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}
import java.nio.file.Files

class SchemaToBytesTest extends AnyFlatSpec with Matchers {

  it should "infer CSV schema, save it to file, and reuse it for the next reading" in {
    val airportsFile = getClass.getResource("airports.csv")
    airportsFile should not be null

    val df1 = Factory.ss.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(airportsFile.getPath)

    val inferredSchema = df1.schema
    val schemaFile = Files.createTempFile(getClass.getSimpleName, ".schema").toFile
    val out = new ObjectOutputStream(new FileOutputStream(schemaFile))
    out.writeObject(inferredSchema)
    out.close()

    val ois = new ObjectInputStream(new FileInputStream(schemaFile))
    val schema2 = ois.readObject().asInstanceOf[StructType]
    ois.close()

    val df2 = Factory.ss.read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "false")
      .schema(schema2)
      .load(airportsFile.getPath)

    df1.collect() shouldEqual df2.collect()
  }

}