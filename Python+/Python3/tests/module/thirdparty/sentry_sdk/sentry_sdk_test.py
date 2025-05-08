import sentry_sdk
from sentry_sdk import capture_message


# Source: https://github.com/getsentry/sentry-python?tab=readme-ov-file#quick-usage-example
def test_sentry_sdk():
    sentry_sdk.init("https://12927b5f211046b575ee51fd8b1ac34f@o1.ingest.sentry.io/1", traces_sample_rate=1.0)
    capture_message("Hello Sentry!")  # You'll see this in your Sentry dashboard.
    raise ValueError("Oops, something went wrong!")
