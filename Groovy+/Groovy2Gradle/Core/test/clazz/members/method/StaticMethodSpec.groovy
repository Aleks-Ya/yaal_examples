package clazz.members.method

import spock.lang.Specification

class StaticMethodSpec extends Specification {
    def "main"() {
        expect:
        StaticMethod.getOne() == 1
    }
}
