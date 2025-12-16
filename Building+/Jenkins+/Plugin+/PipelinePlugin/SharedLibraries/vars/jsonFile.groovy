String readPersonNameFromJsonFile() {
    def json = readJSON(file: '/tmp/shared-libraries-git-repo/resources/jsonFile.json')
    echo("readJSON output: $json")
    echo("Class of readJSON output: ${json.getClass()}")
    return json.name
}
