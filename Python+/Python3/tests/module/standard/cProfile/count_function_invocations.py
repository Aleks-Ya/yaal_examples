from pstats import SortKey
from cProfile import Profile
from pstats import Stats
from time import sleep


class Delegate:
    def calculate(self):
        sleep(0.5)


class Worker:
    def __init__(self, delegate: Delegate):
        self.delegate = delegate

    def do_work(self):
        __sum: int = 0
        for i in range(1_000_000_000):
            __sum += i
        print(__sum)
        for i in range(5):
            self.delegate.calculate()


def test_measure_function_invocations():
    profile: Profile = Profile()
    profile.enable()

    delegate: Delegate = Delegate()
    worker: Worker = Worker(delegate)
    worker.do_work()

    profile.disable()
    stats: Stats = Stats(profile).sort_stats(SortKey.CUMULATIVE)
    stats.print_stats()
