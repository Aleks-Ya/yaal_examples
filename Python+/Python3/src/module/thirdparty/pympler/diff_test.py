from pympler.tracker import SummaryTracker


def test_diff():
    tracker: SummaryTracker = SummaryTracker()
    tracker.print_diff()
    s: str = "str"
    tracker.print_diff()
