import custom.StepsAwareHelper
import custom.StringsHelper

String getString() {
    return StringsHelper.getABC()
}

String getStepsAwareString() {
    def helper = new StepsAwareHelper(this)
    return helper.getABC()
}