package hello.appmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
public class AppmasterApplication {

	public static void main(String[] args) {
		System.setProperty("HADOOP_USER_NAME", "root");
		SpringApplication.run(AppmasterApplication.class, args);
	}

}
