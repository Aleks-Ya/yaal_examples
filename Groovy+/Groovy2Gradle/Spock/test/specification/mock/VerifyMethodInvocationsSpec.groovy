package specification.mock


import spock.lang.Specification

class VerifyMethodInvocationsSpec extends Specification {

    def "verify method without parameters"() {
        given:
        def mock = Mock(PersonMary)

        when:
        mock.name()
        mock.name()

        then:
        2 * mock.name()
        0 * mock.age()
        0 * _ //no other invocations
    }

    def "verify method with any parameters"() {
        given:
        def mock = Mock(InputStream)

        when:
        mock.read(new byte[5])
        mock.read(new byte[10])
        mock.read(new byte[10], 1, 2)

        then:
        0 * mock.read()
        2 * mock.read(_)
        1 * mock.read(_, _, _)
        0 * mock.readAllBytes()
        0 * _ //no other invocations
    }

    def "verify method with specific parameters"() {
        given:
        def mock = Mock(InputStream)

        when:
        mock.read(new byte[5])
        mock.read(new byte[10])
        mock.read(new byte[10], 1, 2)

        then:
        1 * mock.read(new byte[5])
        1 * mock.read(new byte[10])
        1 * mock.read(new byte[10], 1, 2)
        0 * mock.readAllBytes()
        0 * _ //no other invocations
    }

}

