package custom

import spock.lang.Specification

class StepsAwareHelperSpec extends Specification {

    def getABC() {
        given:
        Expando mock = new Expando()
        mock.echo = {}

        StepsAwareHelper helper = new StepsAwareHelper(mock)
        String str = helper.getABC()

        expect:
        str == "groovy.util.Expando_ABC"
    }

    def readJsonDocument() {
        given:
        Expando mock = new Expando()
        mock.readJSON = { ['a': '1', 'b': '2'] }

        StepsAwareHelper helper = new StepsAwareHelper(mock)
        Map<String, String> map = helper.readJsonDocument()

        expect:
        map == ['a': '1', 'b': '2']
    }

}