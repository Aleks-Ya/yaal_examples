package flow
/**
 * Цикл for.
 */
class ForCycle {
    static void main(args) {
        list()
        map()
        text()
    }

    static void text() {
        def s = "abcdef"
        for (c in s) {
            print c
        }
    }

    static void map() {
        def map = ['abc':1, 'def':2, 'xyz':3]

        for (entry in map) {
            println entry.key + "=" + entry.value
        }

        for (key in map.keySet()) {
            println key + "=" + map.get(key)
        }

        for (value in map.values()) {
            println(value)
        }

    }

    private static void list() {
        def list = ["one", "two", "three"]

        for (i in list) {
            println i
        }

        for (i in [1, 2, 3]) {
            println i
        }

        for (i in 0..5) {
            println i
        }
        list.each{ el ->
            println ("el+$el")
            println ("el+$el")
        }
    }
}
