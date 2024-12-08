package collection

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class ListTest {

    @Test
    void emptyList() {
        def numbers = []
        assert numbers.size() == 0
    }

    @Test
    void integerList() {
        def numbers = [1, 2, 3, 4, 5]
        def firstElement = numbers[0]
        assert firstElement == 1
    }

    @Test
    void addElement() {
        def numbers = [1, 2]
        assertEquals(numbers, [1, 2])
        numbers.add(3)
        numbers.addFirst(0)
        numbers.addLast(4)
        assertEquals(numbers, [0, 1, 2, 3, 4])
    }

    @Test
    void filterList() {
        def numbers = [1, -2, 0, 4, -5]
        def positive = numbers.findAll { it > 0 }
        assertEquals([1, 4], positive)
    }

    @Test
    void mapList() {
        def numbers = [1, -2, 0]
        def doubledNumbers = numbers.collect { it * 2 }
        assertEquals([2, -4, 0], doubledNumbers)
    }

    @Test
    void forEach() {
        def numbers = [1, -2, 0]
        numbers.forEach { println(it) }
    }

    @Test
    void listToMap() {
        def numbers = [1, -2, 0]
        def numbersMap = numbers.collectEntries { [(it): it * 2] }
        assertEquals([(1): 2, (-2): -4, (0): 0], numbersMap)
    }
}