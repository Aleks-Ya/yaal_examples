package io.print

import spock.lang.Specification

class PrintSpec extends Specification {
    def "format"() {
        expect:
        printf('Size is %d\n', 42)
    }
}
