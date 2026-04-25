package spark3.sql.dataset.encoder

import org.apache.spark.sql.Dataset
import org.apache.spark.sql.catalyst.analysis.GetColumnByOrdinal
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder
import org.apache.spark.sql.catalyst.expressions.objects.{Invoke, NewInstance, StaticInvoke}
import org.apache.spark.sql.catalyst.expressions.{BoundReference, CreateNamedStruct, Literal}
import org.apache.spark.sql.types.{ObjectType, StringType}
import org.apache.spark.unsafe.types.UTF8String
import org.scalatest.flatspec.AnyFlatSpec
import spark3.sql.{Factory, SparkMatchers}

import scala.reflect.classTag

class CustomEncoderTest extends AnyFlatSpec with SparkMatchers {

  it should "use a custom encoder" in {
    import CustomEncoders._
    val data: Seq[HelloWorld] = Seq(new HelloWorld("Hello"), new HelloWorld("World"))
    val ds: Dataset[HelloWorld] = Factory.ss.createDataset(data)

    ds shouldHaveDDL "message STRING NOT NULL"
    ds shouldContain(
      new HelloWorld("Hello"),
      new HelloWorld("World")
    )

    // Deserialization works perfectly
    val ds2: Dataset[HelloWorld] = ds.map(hw => new HelloWorld(hw.message + " from Spark!"))
    ds2 shouldHaveDDL "message STRING NOT NULL"
    ds2 shouldContain(
      new HelloWorld("Hello from Spark!"),
      new HelloWorld("World from Spark!")
    )
  }

}

class HelloWorld(val message: String) {
  override def toString: String = s"HelloWorld($message)"
}

object CustomEncoders {

  implicit def helloWorldEncoder: ExpressionEncoder[HelloWorld] = {
    val cls = classOf[HelloWorld]

    val inputObj = BoundReference(0, ObjectType(cls), nullable = true)
    val getMessage = Invoke(inputObj, "message", ObjectType(classOf[String]), returnNullable = false)
    val makeUTF8String = StaticInvoke(classOf[UTF8String], StringType, "fromString", getMessage :: Nil, returnNullable = false)
    val serializer = CreateNamedStruct(Seq(Literal("message"), makeUTF8String))

    val getColumn = GetColumnByOrdinal(0, StringType)
    val makeString = Invoke(getColumn, "toString", ObjectType(classOf[String]), returnNullable = false)
    val deserializer = NewInstance(cls, makeString :: Nil, ObjectType(cls), propagateNull = false)

    new ExpressionEncoder[HelloWorld](serializer, deserializer, classTag[HelloWorld])
  }
}

