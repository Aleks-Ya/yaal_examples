from pstats import SortKey
import tempfile
from cProfile import Profile
from pstats import Stats
from time import sleep


class Worker:
    def do_work(self):
        sleep(1)


def test_dump_stats_to_binary_file():
    profiler: Profile = Profile()
    profiler.enable()
    worker: Worker = Worker()
    worker.do_work()
    profiler.disable()
    stats: Stats = Stats(profiler).sort_stats(SortKey.CUMULATIVE)

    full_name: str = tempfile.mkstemp(".prof")[1]
    stats.dump_stats(full_name)

    print(full_name)
