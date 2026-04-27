package json

import groovy.json.JsonSlurper
import spock.lang.Specification

class JsonSlurperSpec extends Specification {
    private def jsonString = '''{
                                  "name": "Alice",
                                  "age": 30,
                                  "active": true,
                                  "created": 1777456344,
                                  "hobbies": ["reading", "coding", "gaming"]
                                }'''
    private def parser = new JsonSlurper()

    def "parse JSON string to object"() {
        given:
        Object data = parser.parseText(jsonString)

        expect:
        data.name == "Alice"
        data.age == 30
        data.active == true
        data.created == 1777456344
        data.hobbies == ["reading", "coding", "gaming"]
    }

    def "parse JSON string to map"() {
        given:
        Map data = parser.parseText(jsonString) as Map

        expect:
        data.name == "Alice"
        data.age == 30
        data.active == true
        data.created == 1777456344
        data.hobbies == ["reading", "coding", "gaming"]
    }

    def "parse JSON string to custom class"() {
        given:
        def data = parser.parseText(jsonString)
        Person person = data as Person

        expect:
        person.name == "Alice"
        person.age == 30
        person.active
        person.created == 1777456344
        person.hobbies == ["reading", "coding", "gaming"]
    }

    def "parse JSON string to custom class with different field names"() {
        given:
        def data = parser.parseText(jsonString)
        PersonWithDifferentFields person = new PersonWithDifferentFields(
                fullName: data.name,
                yearsOld: data.age,
                isActive: data.active,
                timestamp: data.created,
                interests: data.hobbies
        )

        expect:
        person.fullName == "Alice"
        person.yearsOld == 30
        person.isActive
        person.timestamp == 1777456344
        person.interests == ["reading", "coding", "gaming"]
    }

    def "parse JSON array string"() {
        given:
        def jsonArrayString = '''[
                                      {"name": "Alice", "age": 30},
                                      {"name": "Bob", "age": 25},
                                      {"name": "Charlie", "age": 35}
                                  ]'''
        List data = parser.parseText(jsonArrayString) as List

        expect:
        data.size() == 3
        data[0].name == "Alice"
        data[0].age == 30
        data[1].name == "Bob"
        data[1].age == 25
        data[2].name == "Charlie"
        data[2].age == 35
    }

    def "parse JSON array string to list of custom class"() {
        given:
        def jsonArrayString = '''[
                                      {"name": "Alice", "age": 30, "active": true, "created": 1777456344, "hobbies": ["reading", "coding"]},
                                      {"name": "Bob", "age": 25, "active": false, "created": 1777456345, "hobbies": ["gaming"]},
                                      {"name": "Charlie", "age": 35, "active": true, "created": 1777456346, "hobbies": ["reading", "gaming"]}
                                  ]'''
        def data = parser.parseText(jsonArrayString)
        List<Person> persons = data.collect { it as Person }

        expect:
        persons.size() == 3
        persons[0].name == "Alice"
        persons[0].age == 30
        persons[0].active
        persons[0].created == 1777456344
        persons[0].hobbies == ["reading", "coding"]
        persons[1].name == "Bob"
        persons[1].age == 25
        !persons[1].active
        persons[1].created == 1777456345
        persons[1].hobbies == ["gaming"]
        persons[2].name == "Charlie"
        persons[2].age == 35
        persons[2].active
        persons[2].created == 1777456346
        persons[2].hobbies == ["reading", "gaming"]
    }

}

class Person {
    String name
    int age
    boolean active
    long created
    List<String> hobbies
}

class PersonWithDifferentFields {
    String fullName
    int yearsOld
    boolean isActive
    long timestamp
    List<String> interests
}

