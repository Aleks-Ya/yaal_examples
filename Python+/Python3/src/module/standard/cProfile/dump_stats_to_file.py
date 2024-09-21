import tempfile
from cProfile import Profile
from time import sleep


class Worker:
    def do_work(self):
        sleep(1)


def test_dump_stats_file():
    profile: Profile = Profile()
    profile.enable()
    worker: Worker = Worker()
    worker.do_work()
    profile.disable()

    full_name: str = tempfile.mkstemp(".prof")[1]
    profile.dump_stats(full_name)
    print(full_name)
