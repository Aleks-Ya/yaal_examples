package subtype.resolver;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Polymorphic Deserialization
 */
public class SubtypeResolverTest {
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void parent() throws IOException {
		Parent expParent = new Parent();
		expParent.setName("my name");
		String parentJson = mapper.writeValueAsString(expParent);
		assertEquals("{\"name\":\"my name\"}", parentJson);
		Parent actParent = mapper.readValue(parentJson, Parent.class);
		assertEquals(expParent, actParent);
	}
	
	@Test
	public void child() throws IOException {
		Child expChild = new Child();
		expChild.setName("my name");
		expChild.setNumber(1);
		String childJson = mapper.writeValueAsString(expChild);
		assertEquals("{\"name\":\"my name\",\"number\":1}", childJson);
		Child actChild = mapper.readValue(childJson, Child.class);
		assertEquals(expChild, actChild);
	}
	
	@Test
	public void defaultTyping() throws IOException {
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		
		Child expChild = new Child();
		expChild.setName("my name");
		expChild.setNumber(1);
		String childJson = mapper.writeValueAsString(expChild);
		assertEquals("[\"subtype.resolver.Child\",{\"name\":\"my name\",\"number\":1}]", childJson);
		Parent actChild = mapper.readValue(childJson, Parent.class);
		assertEquals(expChild, actChild);
	}

}
