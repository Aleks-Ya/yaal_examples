package vaadin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VaadinKotlinJava12MavenExampleApplication

fun main(args: Array<String>) {
	runApplication<VaadinKotlinJava12MavenExampleApplication>(*args)
}
