from pstats import Stats


def test_load_dump_file():
    stats: Stats = Stats('profiler_report.prof')
    stats.print_stats()
