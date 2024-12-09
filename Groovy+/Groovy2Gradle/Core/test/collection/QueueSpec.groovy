package collection


import spock.lang.Specification

class QueueSpec extends Specification {

    def "create queue"() {
        given:
        Queue<String> queue = new LinkedList<>()
        queue.add("a")
        queue.add("b")
        queue.add("c")
        queue.addFirst("z")

        expect:
        queue.asList() == ["z", "a", "b", "c"]
    }

    def "move element to the end"() {
        given:
        Queue<String> queue = new LinkedList<>(["a", "b", "c"])
        def first = queue.remove()
        queue.add(first)

        expect:
        queue.asList() == ["b", "c", "a"]
    }

}