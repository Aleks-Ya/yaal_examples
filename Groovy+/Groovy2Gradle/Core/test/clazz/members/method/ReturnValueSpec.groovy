package clazz.members.method

import spock.lang.Specification

class MyClass {
    static List<String> getStringList() {
        return ["a", "b"]
    }

    static void returnVoid() {
    }
}

class MyClassSpec extends Specification {

    def "should return correct string list"() {
        expect:
        MyClass.getStringList() == ["a", "b"]
    }

    def "should execute returnVoid method"() {
        when:
        MyClass.returnVoid()

        then:
        noExceptionThrown()
    }
}

