package custom

class StepsAwareHelper {
    private def steps

    StepsAwareHelper(def steps) {
        this.steps = steps
    }

    String getABC() {
        def className = steps.getClass().getName()
        steps.echo("Steps class name: '$className'")
        return className + "_ABC"
    }

    Map<String, String> readJsonDocument() {
        return steps.readJSON("document.json")
    }

}