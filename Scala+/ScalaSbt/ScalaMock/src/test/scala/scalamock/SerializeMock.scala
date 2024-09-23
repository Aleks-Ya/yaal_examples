package scalamock

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, NotSerializableException, ObjectInputStream, ObjectOutputStream}

class SerializeMock extends AnyFlatSpec with Matchers with MockFactory {

  it should "serialize and deserialize a mock" in {
    class Country {
      def getName = "Philippines"
    }
    val countryMock: Country = mock[Country]
    (countryMock.getName _).expects().returns("Germany")
    countryMock.getName shouldEqual "Germany"

    assertThrows[NotSerializableException] {
      val os = new ByteArrayOutputStream()
      val oos = new ObjectOutputStream(os)
      oos.writeObject(countryMock)

      val is = new ByteArrayInputStream(os.toByteArray)
      val ois = new ObjectInputStream(is)
      val deserializedMock = ois.readObject().asInstanceOf[Country]
      deserializedMock.getName shouldEqual "Germany"
    }
  }

}