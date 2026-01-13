package scala.system

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util
import java.util.Properties

class SystemTest extends AnyFlatSpec with Matchers {

  it should "print env vars" in {
    val envs: util.Map[String, String] = System.getenv
    println(s"Env vars: $envs")
  }

  it should "print Java properties" in {
    val props: Properties = System.getProperties
    println(s"Java properties: $props")
  }

}
