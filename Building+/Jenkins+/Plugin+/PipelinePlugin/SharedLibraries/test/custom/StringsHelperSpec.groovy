package custom

import spock.lang.Specification

class StringsHelperSpec extends Specification {

    def filterJobsByName() {
        given:
        String str = StringsHelper.getABC()

        expect:
        str == "ABC"
    }

}