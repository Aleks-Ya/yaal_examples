package collection.array


import spock.lang.Specification

class ArraySpec extends Specification {

    def "declare arrays"() {
        setup:
        def arr1 = new String[]{"A", "B"}
        String[] arr2 = ["A", "B"]
        def arr3 = ["A", "B"] as String[]
        def arr4 = ["A", "B"]

        expect:
        arr1.class == String[]
        arr2.class == String[]
        arr3.class == String[]
        arr4.class == ArrayList
    }

    def "filter array elements"() {
        setup:
        Integer[] numbers = [10, 5, 16, 33]
        def bigNumbers = numbers.findAll { it > 10 }

        expect:
        bigNumbers.class == ArrayList
        bigNumbers == [16, 33]
    }

}