package flow

import spock.lang.Specification

class WhileSpec extends Specification {

    def "test while loop"() {
        given: "an initial value of i"
        def i = 0

        expect: "i is printed and incremented until it reaches 5"
        while (i < 5) {
            println(i)
            i++
        }
    }
}
