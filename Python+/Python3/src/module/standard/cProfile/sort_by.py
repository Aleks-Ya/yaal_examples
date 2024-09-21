from pstats import SortKey
from cProfile import Profile
from pstats import Stats
from time import sleep


class Worker:
    def do_work(self):
        sleep(1)
        for i in range(30):
            self.calculate()

    def calculate(self) -> None:
        sleep(0.1)


def test_sort_by_different_columns():
    profile: Profile = Profile()
    profile.enable()
    worker: Worker = Worker()
    worker.do_work()
    profile.disable()

    print("\n\nStats by cumulative time:")
    Stats(profile).sort_stats(SortKey.CUMULATIVE).print_stats()

    print("\n\nStats by call number:")
    Stats(profile).sort_stats(SortKey.CALLS).print_stats()

    print("\n\nStats by total time:")
    Stats(profile).sort_stats(SortKey.TIME).print_stats()
