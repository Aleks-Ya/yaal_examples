package neuralsearch

import org.opensearch.common.settings.Settings
import org.opensearch.env.Environment
import org.opensearch.env.Environment.PATH_HOME_SETTING
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.nio.file.Paths

class EnvironmentTest extends AnyFlatSpec with Matchers {

  it should "use Environment" in {
    val homePath = Paths.get("/tmp/chunking/home")
    val configPath = Paths.get("/tmp/chunking/config")
    val key = PATH_HOME_SETTING.getKey
    val settings = Settings.builder.put(key, homePath).build
    val environment = new Environment(settings, configPath)
    val actValue = environment.settings().get(key)
    actValue shouldBe homePath.toString
  }

}
