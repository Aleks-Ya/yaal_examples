# Tracking object lifetime
from pympler import classtracker


class Document:
    title = 1


tr = classtracker.ClassTracker()
tr.track_class(Document)
tr.create_snapshot()

d1 = Document()
tr.create_snapshot()

d2 = Document()
tr.create_snapshot()

tr.stats.print_summary()
