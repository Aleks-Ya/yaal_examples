package specification

import spock.lang.Ignore
import spock.lang.Specification


class StackSpec extends Specification {

    @Ignore
    def "adding an element leads to size increase"() {
        setup: "a new stack instance is created"
        def stack = new Stack()

        when:
        stack.push 42

        then:
        stack.size() == 1
    }
}
