package serializer.factory;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializerFactoryTest {

	@Test
	public void test() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializerFactory(null);
	}

}
