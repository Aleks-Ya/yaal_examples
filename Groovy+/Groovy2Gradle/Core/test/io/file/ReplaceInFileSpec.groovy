package io.file

import spock.lang.Specification

class ReplaceInFileSpec extends Specification {

    def "replace substring in file"() {
        given: "a temporary file with text 'abc'"
        def file = File.createTempFile(ReplaceInFileSpec.simpleName, '.tmp')
        file.text = 'abc'

        expect: "the file's contents should initially be 'abc'"
        new File(file.absolutePath).text == 'abc'

        when: "replacing 'b' with 'x' in the file"
        replace(file.absolutePath, "b", "x")

        then: "the file's contents should be 'axc'"
        new File(file.absolutePath).text == 'axc'
    }

    private static def replace(String file, String from, String to) {
        def f = new File(file)
        f.text = f.text.replace(from, to)
    }
}
