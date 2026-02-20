from threading import Thread


def test_custom_thread_name():
    t: Thread = Thread(target=lambda: print("running"), name="MyWorkerThread")
    t.start()
    t.join()
    assert t.name == "MyWorkerThread"
