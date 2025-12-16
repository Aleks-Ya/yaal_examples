@Library('custom') _

node {
    echo 'Hello, Jenkins Pipeline!'

    def text = additionalStringsUtil.getString()
    echo "The string: $text"

    def stepsAwareText = additionalStringsUtil.getStepsAwareString()
    echo "The steps aware string: $stepsAwareText"

    def personName = jsonFile.readPersonNameFromJsonFile()
    echo "Person name from JSON file: $personName"
}