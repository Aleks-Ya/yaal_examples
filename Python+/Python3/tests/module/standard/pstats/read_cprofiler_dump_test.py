import os
from pstats import Stats


def test_load_dump_file():
    file: str = os.path.join(os.path.dirname(__file__), 'profiler_report.prof')
    stats: Stats = Stats(file)
    stats.print_stats()
