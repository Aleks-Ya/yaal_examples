package my

import com.google.common.base.Strings
import org.apache.commons.lang3.StringUtils

class MyMain {
	def main(args: Array[String]): Unit = {
		Strings.emptyToNull("")
		StringUtils.isBlank(null)
	}
}