String basePath = 'JobOnly'

folder(basePath) {
    description 'This example shows basic folder/job creation.'
}

def person1 = 'John'
def person2 = readFileFromWorkspace('jobs/person.txt')
def scriptName = getClass().getSimpleName().replace('.groovy', '')
job("$basePath/$scriptName") {
    steps {
        shell "echo 'Hello, $person1 and $person2!'"
    }
}
