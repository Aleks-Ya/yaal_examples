package ru.yaal.spring.mvc.controller.argument;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Usage @RequstBody
 * Test: curl --data "login=aleks&password=secr" localhost:8080/request_body_message_converter
 */
@RestController
public class RequestBodyController {

	@RequestMapping(value = "/request_body_message_converter", method = RequestMethod.POST)
	public String requestParam(@RequestBody User user) {
		return "@RequestBody user: " + user;
	}

	/**
	 * Convert body "login=aaa&password=bbb" to User object.
	 */
	@Component
	public static class UserConverter extends AbstractHttpMessageConverter<User> {

		public UserConverter() {
			super(new MediaType("application", "x-www-form-urlencoded", Charset.forName("UTF-8")));
		}
		
		@Override
		protected User readInternal(Class<? extends User> clazz, HttpInputMessage message)
				throws IOException, HttpMessageNotReadableException {
			try (InputStream is = message.getBody()) {
				String[] body = Streams.asString(is).split("&");
				String login = body[0].replace("login=", "");
				String password = body[1].replace("password=", "");
				return new User(login, password);
			}
		}

		@Override
		protected boolean supports(Class<?> clazz) {
			return clazz.equals(User.class);
		}

		@Override
		protected void writeInternal(User user, HttpOutputMessage message)
				throws IOException, HttpMessageNotWritableException {
			try (DataOutputStream dos = new DataOutputStream(message.getBody())) {
				String out = user.getLogin() + "&" + user.getPassword();
				dos.writeUTF(out);
			}
		}
	}

	public static class User {
		private String login;
		private String password;

		public User(String login, String password) {
			this.login = login;
			this.password = password;
		}

		private String getPassword() {
			return password;
		}

		private String getLogin() {
			return login;
		}

		@Override
		public String toString() {
			return "User [login=" + login + ", password=" + password + "]";
		}
	}
}