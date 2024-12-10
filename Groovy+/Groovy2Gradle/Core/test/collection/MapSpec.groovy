package collection

import spock.lang.Specification

class MapSpec extends Specification {

    def "test map operations"() {
        given:
        def map = [name: "Gromit", likes: "cheese", id: 1234]

        expect:
        map.get("name") == "Gromit"
        map.get("id") == 1234
        map["name"] == "Gromit"
        map['id'] == 1234
        map instanceof Map

        when:
        def emptyMap = [:]

        then:
        emptyMap.size() == 0

        when:
        emptyMap.put("foo", 5)

        then:
        emptyMap.size() == 1
        emptyMap.get("foo") == 5
    }

    def "check if map contains keys"() {
        given:
        def map = ["John": 30, "Mary": 25]

        expect:
        map.containsKey("Mary")
        !map.containsKey("Rick")
    }

    def "get keys"() {
        given:
        def map = ["John": 30, "Mary": 25]

        when:
        def keys = map.keySet()

        then:
        keys == ["John", "Mary"] as Set
    }

    def "map size"() {
        given:
        def map = ["John": 30, "Mary": 25]

        expect:
        map.size() == 2
    }

    def "get keys not in list"() {
        given:
        def nameMap = ["John": 30, "Mary": 25, "Mark": 20]
        def names = ["Mark", "John"]

        when:
        def namesNotInList = nameMap.keySet().findAll { !names.contains(it) }

        then:
        assert namesNotInList == ["Mary"] as Set
    }
}
