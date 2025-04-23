from pympler.tracker import SummaryTracker

tracker: SummaryTracker = SummaryTracker()
tracker.print_diff()
s = "str"
tracker.print_diff()
