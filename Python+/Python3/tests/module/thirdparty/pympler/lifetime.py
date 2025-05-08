# Tracking object lifetime
from pympler import classtracker
from pympler.classtracker import ClassTracker


class Document:
    title = 1


tr: ClassTracker = classtracker.ClassTracker()
tr.track_class(Document)
tr.create_snapshot()

d1: Document = Document()
tr.create_snapshot()

d2: Document = Document()
tr.create_snapshot()

tr.stats.print_summary()
