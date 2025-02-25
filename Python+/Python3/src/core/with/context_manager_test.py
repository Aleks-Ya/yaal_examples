from threading import Lock


def test_reuse_context_manager():
    lock: Lock = Lock()

    with lock:
        print("Lock acquired, executing critical section")

    with lock:
        print("Again")
