package ru.yaal.spring.security.test;

import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * WithMockUser on class
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@WithMockUser
public class WithMockUserOnClassTest {

	@Autowired
	private MessageService messageService;
	
	@Test
	public void getMessageWithMockUser() {
		assertThat(messageService.getMessage(), startsWith("Hello"));
	}
}
