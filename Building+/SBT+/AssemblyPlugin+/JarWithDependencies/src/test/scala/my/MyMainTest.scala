package my

import com.google.common.base.Strings
import org.junit.Assert.fail
import org.apache.commons.lang3.StringUtils

class MyMainTest {
	def test(): Unit = {
		Strings.emptyToNull("")
		StringUtils.isBlank(null)
		fail
	}
}